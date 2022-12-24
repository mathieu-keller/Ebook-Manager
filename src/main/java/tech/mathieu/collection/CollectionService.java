package tech.mathieu.collection;

import org.graalvm.collections.Pair;
import tech.mathieu.book.BookService;
import tech.mathieu.epub.opf.Opf;
import tech.mathieu.epub.opf.metadata.Meta;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Map;
import java.util.Objects;

@Transactional
@ApplicationScoped
public class CollectionService {

    @Inject
    EntityManager entityManager;

    @Inject
    BookService bookService;

    public String getFirstBookFolderFromCollection(Long id) {
        var collection = entityManager.find(CollectionEntity.class, id);
        var bookFolder = collection.getBookEntities()
                .stream()
                .min((a, b) -> {
                    if (a.getGroupPosition() == null || b.getGroupPosition() == null) {
                        return 0;
                    }
                    return (int) (a.getGroupPosition() - b.getGroupPosition());
                });
        if (bookFolder.isPresent()) {
            return bookFolder
                    .get()
                    .getPath();
        }
        throw new IllegalStateException("no book folder, in collection " + id + " , found!");
    }

    public CollectionDto getDtos(Long id) {
        var collection = entityManager.find(CollectionEntity.class, id);
        var books = collection.getBookEntities().stream().map(book -> bookService.getBookDto(book)).toList();
        return new CollectionDto(collection.getId(), collection.getName(), books);
    }

    public Pair<CollectionEntity, Long> getCollection(Opf opf, String path, Map<String, Map<String, Meta>> metaIdMap) {
        var collections = opf.getMetadata().getMeta()
                .stream()
                .filter(meta -> Objects.equals(meta.getProperty(), "belongs-to-collection"))
                .toList();
        if (collections.size() > 1) {
            throw new IllegalArgumentException("only one collection is allowed!");
        }
        if (collections.size() == 1) {
            var collection = collections.get(0);
            var collectionName = collection.getValue().strip();
            var collectionEntity = entityManager.createQuery("select t from collection t where t.name = :name", CollectionEntity.class)
                    .setParameter("name", collectionName)
                    .getResultList()
                    .stream()
                    .findFirst()
                    .orElseGet(() -> {
                        var newEntity = new CollectionEntity();
                        newEntity.setName(collectionName);
                        newEntity.setCoverPath(path + "/cover.jpeg");
                        return newEntity;
                    });
            var id = collection.getId();
            var collectionMetaData = metaIdMap.get("#" + id);
            if (collectionMetaData != null) {
                var collectionType = collectionMetaData.get("collection-type");
                collectionEntity.setType(collectionType.getValue());
                var groupPosition = collectionMetaData.get("group-position");
                return Pair.create(collectionEntity, groupPosition != null ? Long.parseLong(groupPosition.getValue()) : null);
            }
            return Pair.create(collectionEntity, null);
        }
        return Pair.create(null, null);
    }


}

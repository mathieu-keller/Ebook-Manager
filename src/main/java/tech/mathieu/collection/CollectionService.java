package tech.mathieu.collection;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.graalvm.collections.Pair;
import tech.mathieu.book.BookService;
import tech.mathieu.epub.opf.Opf;
import tech.mathieu.epub.opf.metadata.Meta;
import tech.mathieu.exceptions.IllegalArgumentApplicationException;
import tech.mathieu.exceptions.NotFoundApplicationException;

@Transactional
@ApplicationScoped
public class CollectionService {

  @Inject EntityManager entityManager;

  @Inject BookService bookService;

  private Optional<CollectionEntity> getById(Long id) {
    return Optional.ofNullable(entityManager.find(CollectionEntity.class, id));
  }

  private CollectionEntity getByIdExisting(Long id) {
    return getById(id)
        .orElseThrow(
            () -> new NotFoundApplicationException("Collection with id " + id + " not found!"));
  }

  public String getFirstBookCoverPath(Long id) {
    var collection = getByIdExisting(id);
    var bookFolder =
        collection.getBookEntities().stream()
            .min(
                (a, b) -> {
                  if (a.getGroupPosition() == null || b.getGroupPosition() == null) {
                    return 0;
                  }
                  return (int) (a.getGroupPosition() - b.getGroupPosition());
                });
    if (bookFolder.isPresent()) {
      return bookFolder.get().getCoverPath();
    }
    throw new NotFoundApplicationException("no book folder, in collection " + id + " , found!");
  }

  public CollectionDto getDtos(Long id) {
    var collection = getByIdExisting(id);
    var books =
        collection.getBookEntities().stream().map(book -> bookService.getBookDto(book)).toList();
    return new CollectionDto(collection.getId(), collection.getName(), books);
  }

  public Pair<CollectionEntity, Long> getCollection(
      Opf opf, String path, Map<String, Map<String, Meta>> metaIdMap) {
    var collections =
        opf.getMetadata().getMeta().stream()
            .filter(meta -> Objects.equals(meta.getProperty(), "belongs-to-collection"))
            .toList();
    if (collections.size() > 1) {
      throw new IllegalArgumentApplicationException("only one collection is allowed!");
    }
    if (collections.size() == 1) {
      var collection = collections.get(0);
      var collectionName = collection.getValue().strip();
      var collectionEntity =
          entityManager
              .createQuery(
                  "select t from CollectionEntity t where t.name = :name", CollectionEntity.class)
              .setParameter("name", collectionName)
              .getResultList()
              .stream()
              .findFirst()
              .orElseGet(
                  () -> {
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
        return Pair.create(
            collectionEntity,
            groupPosition != null ? Long.parseLong(groupPosition.getValue()) : null);
      }
      return Pair.create(collectionEntity, null);
    }
    return Pair.create(null, null);
  }
}

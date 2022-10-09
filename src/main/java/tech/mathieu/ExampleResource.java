package tech.mathieu;

import io.smallrye.common.annotation.Blocking;
import org.jboss.resteasy.reactive.MultipartForm;
import tech.mathieu.ebook.book.BookEntity;
import tech.mathieu.ebook.creator.CreatorEntity;
import tech.mathieu.ebook.identifier.IdentifierEntity;
import tech.mathieu.epub.Reader;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Path("/api/upload/multi")
@Transactional
public class ExampleResource {

  @Inject
  Reader reader;

  @Inject
  EntityManager entityManager;

  @POST
  @Blocking
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public String hello(@MultipartForm MultipartBody form) throws IOException {
    form.file.forEach(file -> {
      try {
        var epub = reader.read(new FileInputStream(file));
        var book = new BookEntity();
        if (epub.getMetadata().getTitles() != null) {
          book.setTitle(epub.getMetadata().getTitles()
              .stream()
              .map(title -> title.getValue()).
              collect(Collectors.joining(", ")));
        }
        if (epub.getMetadata().getMeta() != null) {
          book.setMeta(epub.getMetadata().getMeta()
              .stream()
              .map(title -> title.getContent()).
              collect(Collectors.joining(", ")));
        }
        if (epub.getMetadata().getDates() != null) {
          book.setDate(epub.getMetadata().getDates()
              .stream()
              .map(title -> title.getValue()).
              collect(Collectors.joining(", ")));
        }
        if (epub.getMetadata().getCreators() != null) {
          book.setCreatorEntities(epub.getMetadata().getCreators()
              .stream()
              .map(title -> title.getValue())
              .map(creator -> {
                var name = creator.strip();
                var entity = entityManager.createQuery("select t from CreatorEntity t where t.name = :name", CreatorEntity.class)
                    .setParameter("name", name)
                    .getResultList()
                    .stream()
                    .findFirst()
                    .orElseGet(() -> new CreatorEntity());
                entity.setName(name);
                return entityManager.merge(entity);
              })
              .toList());
        }
        if (epub.getMetadata().getIdentifiers() != null) {
          book.setIdentifierEntities(epub.getMetadata().getIdentifiers()
              .stream()
              .map(identifier -> {
                var entity = new IdentifierEntity();
                entity.setIdentId(identifier.getId());
                entity.setSchema(identifier.getScheme());
                entity.setName(identifier.getValue());
                entity.setBook(book);
                return entity;
              })
              .toList());
        }
        entityManager.merge(book);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    return "DONE";
  }
}

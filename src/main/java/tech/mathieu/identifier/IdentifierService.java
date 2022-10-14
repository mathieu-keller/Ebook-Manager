package tech.mathieu.identifier;

import tech.mathieu.book.BookEntity;
import tech.mathieu.epub.opf.Opf;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class IdentifierService {

  public List<IdentifierEntity> getIdentifiers(Opf epub, BookEntity book) {
    if (epub.getMetadata().getIdentifiers() != null) {
      return epub.getMetadata().getIdentifiers()
          .stream()
          .map(identifier -> {
            var entity = new IdentifierEntity();
            entity.setIdentId(identifier.getId());
            entity.setSchema(identifier.getScheme());
            entity.setName(identifier.getValue());
            entity.setBook(book);
            return entity;
          })
          .toList();
    }
    return List.of();
  }

}

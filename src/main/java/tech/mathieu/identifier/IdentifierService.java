package tech.mathieu.identifier;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;
import tech.mathieu.book.BookEntity;
import tech.mathieu.epub.opf.Opf;

@ApplicationScoped
@Transactional
public class IdentifierService {

  public List<IdentifierEntity> getIdentifiers(Opf epub, BookEntity book) {
    if (epub.getMetadata().getIdentifiers() != null) {
      return epub.getMetadata().getIdentifiers().stream()
          .map(
              identifier -> {
                var entity = new IdentifierEntity();
                entity.setIdentId(identifier.getId());
                entity.setValue(identifier.getValue());
                entity.setBook(book);
                return entity;
              })
          .toList();
    }
    return List.of();
  }
}

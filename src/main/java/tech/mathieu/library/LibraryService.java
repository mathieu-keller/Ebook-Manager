package tech.mathieu.library;

import tech.mathieu.book.BookEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class LibraryService {

  private static final int PAGE_SIZE = 32;

  @Inject
  EntityManager entityManager;

  public List<LibraryDto> getDtos(Integer page) {
    int p = 1;
    if (page != null) {
      p = page;
    }
    return entityManager.createQuery("select t from BookEntity t", BookEntity.class)
        .setFirstResult((p - 1) * PAGE_SIZE)
        .setMaxResults(PAGE_SIZE)
        .getResultList()
        .stream()
        .map(bookEntity -> new LibraryDto(bookEntity.getId(),
            Optional.ofNullable(bookEntity.getCover())
                .map(cover -> "data:image/jpg;base64," + new String(cover))
                .orElse(null),
            bookEntity.getTitle(),
            "book",
            1L))
        .toList();
  }

}

package tech.mathieu.library;

import org.hibernate.query.Query;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Strings;
import tech.mathieu.book.BookEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class LibraryService {

  private static final int PAGE_SIZE = 32;

  @Inject
  EntityManager entityManager;


  public List<LibraryDto> getDtos(Integer page, String search) {
    int p = 1;
    if (page != null) {
      p = page;
    }
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<LibraryItemEntity> cr = cb.createQuery(LibraryItemEntity.class);
    Root<LibraryItemEntity> root = cr.from(LibraryItemEntity.class);
    cr.select(root);
    if (search != null) {
      var where = Arrays.stream(search.split(" "))
          .filter(param -> !param.strip().equals(""))
          .map(param -> "%" + param + "%")
          .map(param -> cb.like(root.get("searchTerms"), param))
          .toList();
      cr.where(cb.and(where.toArray(new Predicate[0])));
    }
    return entityManager.createQuery(cr)
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

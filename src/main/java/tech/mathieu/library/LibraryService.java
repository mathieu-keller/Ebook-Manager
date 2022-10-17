package tech.mathieu.library;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
    if (search != null) {
      CriteriaBuilder cb = entityManager.getCriteriaBuilder();
      CriteriaQuery<LibrarySearchEntity> cr = cb.createQuery(LibrarySearchEntity.class);
      Root<LibrarySearchEntity> root = cr.from(LibrarySearchEntity.class);
      cr.select(root);
      var where = Arrays.stream(search.split(" "))
          .filter(param -> !param.strip().equals(""))
          .map(param -> "%" + param.toUpperCase(Locale.US) + "%")
          .map(param -> cb.like(cb.upper(root.get("searchTerms")), param))
          .toList();
      cr.where(cb.and(where.toArray(new Predicate[0])));
      return entityManager.createQuery(cr)
          .setFirstResult((p - 1) * PAGE_SIZE)
          .setMaxResults(PAGE_SIZE)
          .getResultList()
          .stream()
          .map(bookEntity -> new LibraryDto(bookEntity.getId(),
              bookEntity.getTitle(),
              "book",
              1L))
          .toList();
    }
    return entityManager.createQuery("select t from LibraryEntity t", LibraryEntity.class)
        .setFirstResult((p - 1) * PAGE_SIZE)
        .setMaxResults(PAGE_SIZE)
        .getResultList()
        .stream()
        .map(libraryItem -> new LibraryDto(libraryItem.getId(),
            libraryItem.getTitle(),
            libraryItem.getItemType(),
            libraryItem.getBookCount()))
        .toList();
  }

}

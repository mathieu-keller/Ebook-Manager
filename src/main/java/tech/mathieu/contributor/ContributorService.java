package tech.mathieu.contributor;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import tech.mathieu.epub.opf.Opf;

@ApplicationScoped
@Transactional
public class ContributorService {

  @Inject EntityManager entityManager;

  public Optional<ContributorEntity> getContributorByName(String name) {
    return entityManager
        .createQuery(
            "select t from ContributorEntity t where t.name = :name", ContributorEntity.class)
        .setParameter("name", name)
        .getResultList()
        .stream()
        .findFirst();
  }

  public List<ContributorEntity> getContributors(Opf epub) {
    if (epub.getMetadata().getContributors() != null) {
      return epub.getMetadata().getContributors().stream()
          .map(
              contributor -> {
                var name = contributor.getValue().strip();
                var entity = getContributorByName(name).orElseGet(ContributorEntity::new);
                entity.setName(name);
                return entity;
              })
          .toList();
    }
    return List.of();
  }
}

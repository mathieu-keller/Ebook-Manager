package tech.mathieu.contributor;

import tech.mathieu.epub.opf.Opf;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class ContributorService {

  @Inject
  EntityManager entityManager;

  public Optional<ContributorEntity> getContributorByName(String name) {
    return entityManager.createQuery("select t from ContributorEntity t where t.name = :name", ContributorEntity.class)
        .setParameter("name", name)
        .getResultList()
        .stream()
        .findFirst();
  }

  public List<ContributorEntity> getContributors(Opf epub) {
    if (epub.getMetadata().getContributors() != null) {
      return epub.getMetadata().getContributors()
          .stream()
          .map(contributor -> {
            var name = contributor.getValue().strip();
            var entity = getContributorByName(name)
                .orElseGet(ContributorEntity::new);
            entity.setName(name);
            return entity;
          })
          .toList();
    }
    return List.of();
  }
}

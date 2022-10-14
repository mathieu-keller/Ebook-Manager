package tech.mathieu.creator;

import tech.mathieu.epub.opf.Opf;
import tech.mathieu.epub.opf.metadata.Creator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class CreatorService {

  @Inject
  EntityManager entityManager;

  public Optional<CreatorEntity> getCreatorByName(String name) {
    return entityManager.createQuery("select t from CreatorEntity t where t.name = :name", CreatorEntity.class)
        .setParameter("name", name)
        .setMaxResults(1)
        .getResultList()
        .stream()
        .findFirst();
  }

  public List<CreatorEntity> getCreators(Opf epub) {
    if (epub.getMetadata().getCreators() != null) {
      return epub.getMetadata().getCreators()
          .stream()
          .map(Creator::getValue)
          .map(creator -> {
            var name = creator.strip();
            var entity = getCreatorByName(name)
                .orElseGet(CreatorEntity::new);
            entity.setName(name);
            return entity;
          })
          .toList();
    }
    return List.of();
  }

}

package tech.mathieu.creator;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import tech.mathieu.epub.opf.Opf;
import tech.mathieu.epub.opf.metadata.DefaultAttributes;

@ApplicationScoped
@Transactional
public class CreatorService {

  @Inject EntityManager entityManager;

  public Optional<CreatorEntity> getCreatorByName(String name) {
    return entityManager
        .createQuery("select t from CreatorEntity t where t.name = :name", CreatorEntity.class)
        .setParameter("name", name)
        .setMaxResults(1)
        .getResultList()
        .stream()
        .findFirst();
  }

  public List<CreatorEntity> getCreators(Opf epub) {
    if (epub.getMetadata().getCreators() != null) {
      return epub.getMetadata().getCreators().stream()
          .map(DefaultAttributes::getValue)
          .map(
              creator -> {
                var name = creator.strip();
                var entity = getCreatorByName(name).orElseGet(CreatorEntity::new);
                entity.setName(name);
                return entity;
              })
          .toList();
    }
    return List.of();
  }
}

package tech.mathieu.publisher;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import tech.mathieu.epub.opf.Opf;

@ApplicationScoped
@Transactional
public class PublisherService {

  @Inject EntityManager entityManager;

  public Optional<PublisherEntity> getPublisherByName(String name) {
    return entityManager
        .createQuery("select t from PublisherEntity t where t.name = :name", PublisherEntity.class)
        .setParameter("name", name)
        .getResultList()
        .stream()
        .findFirst();
  }

  public List<PublisherEntity> getPublishers(Opf epub) {
    if (epub.getMetadata().getPublishers() != null) {
      return epub.getMetadata().getPublishers().stream()
          .map(
              contributor -> {
                var name = contributor.getValue().strip();
                var entity = getPublisherByName(name).orElseGet(PublisherEntity::new);
                entity.setName(name);
                return entity;
              })
          .toList();
    }
    return List.of();
  }
}

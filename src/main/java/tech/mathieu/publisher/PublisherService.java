package tech.mathieu.publisher;

import tech.mathieu.epub.opf.Opf;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class PublisherService {

  @Inject
  EntityManager entityManager;

  public Optional<PublisherEntity> getPublisherByName(String name) {
    return entityManager.createQuery("select t from PublisherEntity t where t.name = :name", PublisherEntity.class)
        .setParameter("name", name)
        .getResultList()
        .stream()
        .findFirst();
  }

  public List<PublisherEntity> getPublishers(Opf epub) {
    if (epub.getMetadata().getPublishers() != null) {
      return epub.getMetadata().getPublishers()
          .stream()
          .map(contributor -> {
            var name = contributor.getValue().strip();
            var entity = getPublisherByName(name)
                .orElseGet(PublisherEntity::new);
            entity.setName(name);
            return entity;
          })
          .toList();
    }
    return List.of();
  }

}

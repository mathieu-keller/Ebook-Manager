package tech.mathieu.language;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import tech.mathieu.epub.opf.Opf;

@ApplicationScoped
@Transactional
public class LanguageService {

  @Inject EntityManager entityManager;

  public Optional<LanguageEntity> getLanguageByName(String name) {
    return entityManager
        .createQuery("select t from LanguageEntity t where t.name = :name", LanguageEntity.class)
        .setParameter("name", name)
        .getResultList()
        .stream()
        .findFirst();
  }

  public List<LanguageEntity> getLanguages(Opf epub) {
    if (epub.getMetadata().getLanguages() != null) {
      return epub.getMetadata().getLanguages().stream()
          .map(
              contributor -> {
                var name = contributor.getValue().strip();
                var entity = getLanguageByName(name).orElseGet(LanguageEntity::new);
                entity.setName(name);
                return entity;
              })
          .toList();
    }
    return List.of();
  }
}

package tech.mathieu.subject;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import tech.mathieu.epub.opf.Opf;

@ApplicationScoped
@Transactional
public class SubjectService {

  @Inject EntityManager entityManager;

  public Optional<SubjectEntity> getSubjectByName(String name) {
    return entityManager
        .createQuery("select t from SubjectEntity t where t.name = :name", SubjectEntity.class)
        .setParameter("name", name)
        .setMaxResults(1)
        .getResultList()
        .stream()
        .findFirst();
  }

  public List<SubjectEntity> getSubjects(Opf epub) {
    if (epub.getMetadata().getSubjects() != null) {
      return epub.getMetadata().getSubjects().stream()
          .map(
              contributor -> {
                var name = contributor.getValue().strip();
                var entity = getSubjectByName(name).orElseGet(SubjectEntity::new);
                entity.setName(name);
                return entity;
              })
          .toList();
    }
    return List.of();
  }
}

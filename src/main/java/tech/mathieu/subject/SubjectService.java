package tech.mathieu.subject;

import tech.mathieu.epub.opf.Opf;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class SubjectService {

  @Inject
  EntityManager entityManager;

  public Optional<SubjectEntity> getSubjectByName(String name) {
    return entityManager.createQuery("select t from SubjectEntity t where t.name = :name", SubjectEntity.class)
        .setParameter("name", name)
        .setMaxResults(1)
        .getResultList()
        .stream()
        .findFirst();
  }

  public List<SubjectEntity> getSubjects(Opf epub) {
    if (epub.getMetadata().getSubjects() != null) {
      return epub.getMetadata().getSubjects()
          .stream()
          .map(contributor -> {
            var name = contributor.getValue().strip();
            var entity = getSubjectByName(name)
                .orElseGet(SubjectEntity::new);
            entity.setName(name);
            return entity;
          })
          .toList();
    }
    return List.of();
  }

}

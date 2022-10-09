package tech.mathieu.ebook;

import tech.mathieu.epub.Reader;
import tech.mathieu.epub.opf.Opf;
import tech.mathieu.epub.opf.metadata.Creator;
import tech.mathieu.epub.opf.metadata.Date;
import tech.mathieu.epub.opf.metadata.Meta;
import tech.mathieu.epub.opf.metadata.Title;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class BookService {

  @Inject
  EntityManager entityManager;

  @Inject
  Reader reader;

  public BookEntity saveBook(InputStream in) {
    try {
      var epub = reader.read(in);
      var book = new BookEntity();
      book.setTitle(getTitle(epub));
      book.setMeta(getMeta(epub));
      book.setDate(getDates(epub));
      book.setCreatorEntities(getCreators(epub));
      book.setIdentifierEntities(getIdentifiers(epub, book));
      book.setContributorEntities(getContributors(epub));
      book.setLanguageEntities(getLanguages(epub));
      book.setPublisherEntities(getPublishers(epub));
      book.setSubjectEntities(getSubjects(epub));
      return entityManager.merge(book);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private List<SubjectEntity> getSubjects(Opf epub) {
    if (epub.getMetadata().getSubjects() != null) {
      return epub.getMetadata().getSubjects()
          .stream()
          .map(contributor -> {
            var name = contributor.getValue().strip();
            var entity = entityManager.createQuery("select t from SubjectEntity t where t.name = :name", SubjectEntity.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst()
                .orElseGet(SubjectEntity::new);
            entity.setName(name);
            return entity;
          })
          .toList();
    }
    return List.of();
  }

  private List<PublisherEntity> getPublishers(Opf epub) {
    if (epub.getMetadata().getPublishers() != null) {
      return epub.getMetadata().getPublishers()
          .stream()
          .map(contributor -> {
            var name = contributor.getValue().strip();
            var entity = entityManager.createQuery("select t from PublisherEntity t where t.name = :name", PublisherEntity.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst()
                .orElseGet(PublisherEntity::new);
            entity.setName(name);
            return entity;
          })
          .toList();
    }
    return List.of();
  }

  private List<LanguageEntity> getLanguages(Opf epub) {
    if (epub.getMetadata().getLanguages() != null) {
      return epub.getMetadata().getLanguages()
          .stream()
          .map(contributor -> {
            var name = contributor.getValue().strip();
            var entity = entityManager.createQuery("select t from LanguageEntity t where t.name = :name", LanguageEntity.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst()
                .orElseGet(LanguageEntity::new);
            entity.setName(name);
            return entity;
          })
          .toList();
    }
    return List.of();
  }

  private List<ContributorEntity> getContributors(Opf epub) {
    if (epub.getMetadata().getContributors() != null) {
      return epub.getMetadata().getContributors()
          .stream()
          .map(contributor -> {
            var name = contributor.getValue().strip();
            var entity = entityManager.createQuery("select t from ContributorEntity t where t.name = :name", ContributorEntity.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst()
                .orElseGet(ContributorEntity::new);
            entity.setName(name);
            return entity;
          })
          .toList();
    }
    return List.of();
  }

  private List<CreatorEntity> getCreators(Opf epub) {
    if (epub.getMetadata().getCreators() != null) {
      return epub.getMetadata().getCreators()
          .stream()
          .map(Creator::getValue)
          .map(creator -> {
            var name = creator.strip();
            var entity = entityManager.createQuery("select t from CreatorEntity t where t.name = :name", CreatorEntity.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst()
                .orElseGet(CreatorEntity::new);
            entity.setName(name);
            return entity;
          })
          .toList();
    }
    return List.of();
  }

  private List<IdentifierEntity> getIdentifiers(Opf epub, BookEntity book) {
    if (epub.getMetadata().getIdentifiers() != null) {
      return epub.getMetadata().getIdentifiers()
          .stream()
          .map(identifier -> {
            var entity = new IdentifierEntity();
            entity.setIdentId(identifier.getId());
            entity.setSchema(identifier.getScheme());
            entity.setName(identifier.getValue());
            entity.setBook(book);
            return entity;
          })
          .toList();
    }
    return List.of();
  }

  private String getDates(Opf epub) {
    if (epub.getMetadata().getDates() != null) {
      return epub.getMetadata().getDates()
          .stream()
          .map(Date::getValue).
          collect(Collectors.joining(", "));
    }
    return null;
  }

  private String getMeta(Opf epub) {
    if (epub.getMetadata().getMeta() != null) {
      return epub.getMetadata().getMeta()
          .stream()
          .map(Meta::getContent).
          collect(Collectors.joining(", "));
    }
    return null;
  }

  private String getTitle(Opf epub) {
    if (epub.getMetadata().getTitles() != null) {
      return epub.getMetadata().getTitles()
          .stream()
          .map(Title::getValue).
          collect(Collectors.joining(", "));
    }
    return null;
  }


}

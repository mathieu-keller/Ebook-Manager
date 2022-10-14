package tech.mathieu.book;

import tech.mathieu.creator.CreatorDto;
import tech.mathieu.contributor.ContributorEntity;
import tech.mathieu.creator.CreatorEntity;
import tech.mathieu.identifier.IdentifierEntity;
import tech.mathieu.language.LanguageDto;
import tech.mathieu.language.LanguageEntity;
import tech.mathieu.library.LibraryDto;
import tech.mathieu.publisher.PublisherDto;
import tech.mathieu.publisher.PublisherEntity;
import tech.mathieu.subject.SubjectDto;
import tech.mathieu.subject.SubjectEntity;
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
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class BookService {

  private static final int PAGE_SIZE = 32;

  @Inject
  EntityManager entityManager;

  @Inject
  Reader reader;

  public List<LibraryDto> getDtos(Integer page) {
    int p = 1;
    if (page != null) {
      p = page;
    }
    return entityManager.createQuery("select t from BookEntity t", BookEntity.class)
        .setFirstResult((p - 1) * PAGE_SIZE)
        .setMaxResults(PAGE_SIZE)
        .getResultList()
        .stream()
        .map(bookEntity -> new LibraryDto(bookEntity.getId(),
            Optional.ofNullable(bookEntity.getCover())
                .map(cover -> "data:image/jpg;base64," + new String(cover))
                .orElse(null),
            bookEntity.title,
            "book",
            1L))
        .toList();
  }

  public BookDto getBookDto(String title) {
    var entity = entityManager.createQuery("select t from BookEntity t where t.title = :title", BookEntity.class)
        .setParameter("title", title)
        .getSingleResult();
    return new BookDto(entity.getId(),
        entity.getTitle(),
        null,
        Optional.ofNullable(entity.getLanguageEntities())
            .map(languageEntities -> languageEntities
                .stream()
                .map(languageEntity -> new LanguageDto(languageEntity.getId(), languageEntity.getName()))
                .toList()).orElse(null),
        Optional.ofNullable(entity.getSubjectEntities())
            .map(subjectEntities -> subjectEntities
                .stream()
                .map(subjectEntity -> new SubjectDto(subjectEntity.getId(), subjectEntity.getName()))
                .toList()).orElse(null),
        Optional.ofNullable(entity.getPublisherEntities())
            .map(publisherEntities -> publisherEntities
                .stream()
                .map(publisherEntity -> new PublisherDto(publisherEntity.getId(), publisherEntity.getName()))
                .toList()).orElse(null),
        Optional.ofNullable(entity.getCover())
            .map(cover -> "data:image/jpg;base64," + new String(cover))
            .orElse(null),
        Optional.ofNullable(entity.getCreatorEntities())
            .map(creatorEntities -> creatorEntities
                .stream()
                .map(creatorEntity -> new CreatorDto(creatorEntity.getId(), creatorEntity.getName()))
                .toList()).orElse(null),
        null,
        null
    );
  }

  public BookEntity saveBook(InputStream in) {
    try {
      var epub = reader.read(in);
      var opf = epub.opf();
      var book = new BookEntity();
      book.setCover(epub.cover());
      book.setTitle(getTitle(opf));
      book.setMeta(getMeta(opf));
      book.setDate(getDates(opf));
      book.setCreatorEntities(getCreators(opf));
      book.setIdentifierEntities(getIdentifiers(opf, book));
      book.setContributorEntities(getContributors(opf));
      book.setLanguageEntities(getLanguages(opf));
      book.setPublisherEntities(getPublishers(opf));
      book.setSubjectEntities(getSubjects(opf));
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

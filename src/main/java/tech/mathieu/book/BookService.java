package tech.mathieu.book;

import tech.mathieu.contributor.ContributorService;
import tech.mathieu.creator.CreatorDto;
import tech.mathieu.contributor.ContributorEntity;
import tech.mathieu.creator.CreatorEntity;
import tech.mathieu.creator.CreatorService;
import tech.mathieu.identifier.IdentifierEntity;
import tech.mathieu.identifier.IdentifierService;
import tech.mathieu.language.LanguageDto;
import tech.mathieu.language.LanguageEntity;
import tech.mathieu.language.LanguageService;
import tech.mathieu.library.LibraryDto;
import tech.mathieu.publisher.PublisherDto;
import tech.mathieu.publisher.PublisherEntity;
import tech.mathieu.publisher.PublisherService;
import tech.mathieu.subject.SubjectDto;
import tech.mathieu.subject.SubjectEntity;
import tech.mathieu.epub.Reader;
import tech.mathieu.epub.opf.Opf;
import tech.mathieu.epub.opf.metadata.Creator;
import tech.mathieu.epub.opf.metadata.Date;
import tech.mathieu.epub.opf.metadata.Meta;
import tech.mathieu.epub.opf.metadata.Title;
import tech.mathieu.subject.SubjectService;

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

  @Inject
  EntityManager entityManager;

  @Inject
  SubjectService subjectService;

  @Inject
  CreatorService creatorService;

  @Inject
  PublisherService publisherService;

  @Inject
  LanguageService languageService;

  @Inject
  ContributorService contributorService;

  @Inject
  IdentifierService identifierService;

  @Inject
  Reader reader;


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
      book.setCreatorEntities(creatorService.getCreators(opf));
      book.setIdentifierEntities(identifierService.getIdentifiers(opf, book));
      book.setContributorEntities(contributorService.getContributors(opf));
      book.setLanguageEntities(languageService.getLanguages(opf));
      book.setPublisherEntities(publisherService.getPublishers(opf));
      book.setSubjectEntities(subjectService.getSubjects(opf));
      return entityManager.merge(book);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
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

package tech.mathieu.factories;

import java.util.List;
import tech.mathieu.book.BookEntity;
import tech.mathieu.collection.CollectionEntity;
import tech.mathieu.contributor.ContributorEntity;
import tech.mathieu.creator.CreatorEntity;
import tech.mathieu.identifier.IdentifierEntity;
import tech.mathieu.language.LanguageEntity;
import tech.mathieu.publisher.PublisherEntity;
import tech.mathieu.subject.SubjectEntity;
import tech.mathieu.title.TitleEntity;

public class BookEntityBuilder {
  private Long id;
  private String date = "2022-01-01";
  private String meta;
  private String bookPath = "upload/ebooks/test/test.epub";
  private String coverPath = "upload/ebooks/test/test.jpeg";
  private List<CreatorEntity> creatorEntities;
  private List<ContributorEntity> contributorEntities;
  private List<LanguageEntity> languageEntities;
  private List<PublisherEntity> publisherEntities;
  private List<SubjectEntity> subjectEntities;
  private List<IdentifierEntity> identifierEntities;
  private List<TitleEntity> titleEntities;
  private CollectionEntity collectionEntity;
  private Long groupPosition;

  public BookEntityBuilder withId(Long id) {
    this.id = id;
    return this;
  }

  public BookEntityBuilder withDate(String date) {
    this.date = date;
    return this;
  }

  public BookEntityBuilder withMeta(String meta) {
    this.meta = meta;
    return this;
  }

  public BookEntityBuilder withBookPath(String bookPath) {
    this.bookPath = bookPath;
    return this;
  }

  public BookEntityBuilder withCoverPath(String coverPath) {
    this.coverPath = coverPath;
    return this;
  }

  public BookEntityBuilder withCreatorEntities(List<CreatorEntity> creatorEntities) {
    this.creatorEntities = creatorEntities;
    return this;
  }

  public BookEntityBuilder withContributorEntities(List<ContributorEntity> contributorEntities) {
    this.contributorEntities = contributorEntities;
    return this;
  }

  public BookEntityBuilder withLanguageEntities(List<LanguageEntity> languageEntities) {
    this.languageEntities = languageEntities;
    return this;
  }

  public BookEntityBuilder withPublisherEntities(List<PublisherEntity> publisherEntities) {
    this.publisherEntities = publisherEntities;
    return this;
  }

  public BookEntityBuilder withSubjectEntities(List<SubjectEntity> subjectEntities) {
    this.subjectEntities = subjectEntities;
    return this;
  }

  public BookEntityBuilder withIdentifierEntities(List<IdentifierEntity> identifierEntities) {
    this.identifierEntities = identifierEntities;
    return this;
  }

  public BookEntityBuilder withTitleEntities(List<TitleEntity> titleEntities) {
    this.titleEntities = titleEntities;
    return this;
  }

  public BookEntityBuilder withCollectionEntity(CollectionEntity collectionEntity) {
    this.collectionEntity = collectionEntity;
    return this;
  }

  public BookEntityBuilder withGroupPosition(Long groupPosition) {
    this.groupPosition = groupPosition;
    return this;
  }

  public BookEntity build() {
    var bookEntity = new BookEntity();
    bookEntity.setId(this.id);
    bookEntity.setDate(this.date);
    bookEntity.setMeta(this.meta);
    bookEntity.setBookPath(this.bookPath);
    bookEntity.setCoverPath(this.coverPath);
    bookEntity.setCreatorEntities(this.creatorEntities);
    bookEntity.setContributorEntities(this.contributorEntities);
    bookEntity.setLanguageEntities(this.languageEntities);
    bookEntity.setPublisherEntities(this.publisherEntities);
    bookEntity.setSubjectEntities(this.subjectEntities);
    bookEntity.setIdentifierEntities(this.identifierEntities);
    bookEntity.setTitleEntities(this.titleEntities);
    bookEntity.setCollectionEntity(this.collectionEntity);
    bookEntity.setGroupPosition(this.groupPosition);
    return bookEntity;
  }
}

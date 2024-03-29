package tech.mathieu.book;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import tech.mathieu.collection.CollectionEntity;
import tech.mathieu.contributor.ContributorEntity;
import tech.mathieu.creator.CreatorEntity;
import tech.mathieu.identifier.IdentifierEntity;
import tech.mathieu.language.LanguageEntity;
import tech.mathieu.publisher.PublisherEntity;
import tech.mathieu.subject.SubjectEntity;
import tech.mathieu.title.TitleEntity;

@Entity
@Table(name = "book")
public class BookEntity {
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Id
  @Column(name = "id", nullable = false)
  Long id;

  @Column(name = "date", nullable = true, length = 4000)
  String date;

  @Column(name = "meta", nullable = true, length = 16000)
  String meta;

  @Column(name = "book_path")
  String bookPath;

  @Column(name = "cover_path")
  String coverPath;

  @ManyToMany(
      targetEntity = CreatorEntity.class,
      fetch = FetchType.LAZY,
      cascade = CascadeType.MERGE)
  @JoinTable(
      name = "CREATOR2BOOK",
      joinColumns = @JoinColumn(name = "BOOK_ID"),
      inverseJoinColumns = @JoinColumn(name = "CREATOR_ID"))
  List<CreatorEntity> creatorEntities;

  @ManyToMany(
      targetEntity = ContributorEntity.class,
      fetch = FetchType.LAZY,
      cascade = CascadeType.MERGE)
  @JoinTable(
      name = "contributor2book",
      joinColumns = @JoinColumn(name = "BOOK_ID"),
      inverseJoinColumns = @JoinColumn(name = "contributor_id"))
  List<ContributorEntity> contributorEntities;

  @ManyToMany(
      targetEntity = LanguageEntity.class,
      fetch = FetchType.LAZY,
      cascade = CascadeType.MERGE)
  @JoinTable(
      name = "language2book",
      joinColumns = @JoinColumn(name = "BOOK_ID"),
      inverseJoinColumns = @JoinColumn(name = "language_id"))
  List<LanguageEntity> languageEntities;

  @ManyToMany(
      targetEntity = PublisherEntity.class,
      fetch = FetchType.LAZY,
      cascade = CascadeType.MERGE)
  @JoinTable(
      name = "publisher2book",
      joinColumns = @JoinColumn(name = "BOOK_ID"),
      inverseJoinColumns = @JoinColumn(name = "publisher_id"))
  List<PublisherEntity> publisherEntities;

  @ManyToMany(
      targetEntity = SubjectEntity.class,
      fetch = FetchType.LAZY,
      cascade = CascadeType.MERGE)
  @JoinTable(
      name = "subject2book",
      joinColumns = @JoinColumn(name = "BOOK_ID"),
      inverseJoinColumns = @JoinColumn(name = "subject_id"))
  List<SubjectEntity> subjectEntities;

  @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  List<IdentifierEntity> identifierEntities;

  @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  List<TitleEntity> titleEntities;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "COLLECTION_ID")
  CollectionEntity collectionEntity;

  @Column(name = "GROUP_POSITION")
  Long groupPosition;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getMeta() {
    return meta;
  }

  public void setMeta(String meta) {
    this.meta = meta;
  }

  public String getBookPath() {
    return bookPath;
  }

  public void setBookPath(String bookPath) {
    this.bookPath = bookPath;
  }

  public String getCoverPath() {
    return coverPath;
  }

  public void setCoverPath(String coverPath) {
    this.coverPath = coverPath;
  }

  public List<CreatorEntity> getCreatorEntities() {
    return creatorEntities;
  }

  public void setCreatorEntities(List<CreatorEntity> creatorEntities) {
    this.creatorEntities = creatorEntities;
  }

  public List<ContributorEntity> getContributorEntities() {
    return contributorEntities;
  }

  public void setContributorEntities(List<ContributorEntity> contributorEntities) {
    this.contributorEntities = contributorEntities;
  }

  public List<LanguageEntity> getLanguageEntities() {
    return languageEntities;
  }

  public void setLanguageEntities(List<LanguageEntity> languageEntities) {
    this.languageEntities = languageEntities;
  }

  public List<PublisherEntity> getPublisherEntities() {
    return publisherEntities;
  }

  public void setPublisherEntities(List<PublisherEntity> publisherEntities) {
    this.publisherEntities = publisherEntities;
  }

  public List<SubjectEntity> getSubjectEntities() {
    return subjectEntities;
  }

  public void setSubjectEntities(List<SubjectEntity> subjectEntities) {
    this.subjectEntities = subjectEntities;
  }

  public List<IdentifierEntity> getIdentifierEntities() {
    return identifierEntities;
  }

  public void setIdentifierEntities(List<IdentifierEntity> identifierEntities) {
    this.identifierEntities = identifierEntities;
  }

  public List<TitleEntity> getTitleEntities() {
    return titleEntities;
  }

  public void setTitleEntities(List<TitleEntity> titleEntities) {
    this.titleEntities = titleEntities;
  }

  public CollectionEntity getCollectionEntity() {
    return collectionEntity;
  }

  public void setCollectionEntity(CollectionEntity collectionEntity) {
    this.collectionEntity = collectionEntity;
  }

  public Long getGroupPosition() {
    return groupPosition;
  }

  public void setGroupPosition(Long groupPosition) {
    this.groupPosition = groupPosition;
  }
}

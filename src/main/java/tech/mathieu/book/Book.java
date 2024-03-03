package tech.mathieu.book;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntityBase;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "Book")
public class Book extends ReactivePanacheMongoEntityBase {
  @BsonId private String id;

  private String title;

  private List<String> subjects;

  private List<String> languages;

  private List<String> contributors;

  private List<String> creators;

  private Instant publicationDate;

  public String getId() {
    return id;
  }

  public Book setId(String id) {
    this.id = id;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public Book setTitle(String title) {
    this.title = title;
    return this;
  }

  public List<String> getSubjects() {
    return subjects;
  }

  public Book setSubjects(List<String> subjects) {
    this.subjects = subjects;
    return this;
  }

  public List<String> getLanguages() {
    return languages;
  }

  public Book setLanguages(List<String> languages) {
    this.languages = languages;
    return this;
  }

  public List<String> getContributors() {
    return contributors;
  }

  public Book setContributors(List<String> contributors) {
    this.contributors = contributors;
    return this;
  }

  public List<String> getCreators() {
    return creators;
  }

  public Book setCreators(List<String> creators) {
    this.creators = creators;
    return this;
  }

  public Instant getPublicationDate() {
    return publicationDate;
  }

  public Book setPublicationDate(Instant publicationDate) {
    this.publicationDate = publicationDate;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Book book = (Book) o;
    return Objects.equals(id, book.id)
        && Objects.equals(title, book.title)
        && Objects.equals(subjects, book.subjects)
        && Objects.equals(languages, book.languages)
        && Objects.equals(contributors, book.contributors)
        && Objects.equals(creators, book.creators)
        && Objects.equals(publicationDate, book.publicationDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, subjects, languages, contributors, creators, publicationDate);
  }

  @Override
  public String toString() {
    return "Book{"
        + "id='"
        + id
        + '\''
        + ", title='"
        + title
        + '\''
        + ", subjects="
        + subjects
        + ", languages="
        + languages
        + ", contributors="
        + contributors
        + ", creators="
        + creators
        + ", publicationDate="
        + publicationDate
        + '}';
  }
}

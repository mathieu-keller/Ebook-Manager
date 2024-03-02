package tech.mathieu.book;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntityBase;
import java.util.List;
import java.util.Objects;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "Book")
public class Book extends ReactivePanacheMongoEntityBase {
  @BsonId private String id;

  private List<Title> titles;

  private List<String> subjects;

  public String getId() {
    return id;
  }

  public Book setId(String id) {
    this.id = id;
    return this;
  }

  public List<Title> getTitles() {
    return titles;
  }

  public Book setTitles(List<Title> titles) {
    this.titles = titles;
    return this;
  }

  public List<String> getSubjects() {
    return subjects;
  }

  public Book setSubjects(List<String> subjects) {
    this.subjects = subjects;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Book book = (Book) o;
    return Objects.equals(id, book.id)
        && Objects.equals(titles, book.titles)
        && Objects.equals(subjects, book.subjects);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, titles, subjects);
  }

  @Override
  public String toString() {
    return "Book{" + "id='" + id + '\'' + ", titles=" + titles + ", subjects=" + subjects + '}';
  }
}

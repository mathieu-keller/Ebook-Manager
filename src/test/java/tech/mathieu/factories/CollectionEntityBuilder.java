package tech.mathieu.factories;

import java.util.List;
import tech.mathieu.book.BookEntity;
import tech.mathieu.collection.CollectionEntity;

public class CollectionEntityBuilder {
  private Long id;
  private String name = "test-collection";
  private String coverPath = "upload/ebooks/test/test.jpeg";
  private String type = "series";
  private List<BookEntity> bookEntities;

  public CollectionEntityBuilder withId(Long id) {
    this.id = id;
    return this;
  }

  public CollectionEntityBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public CollectionEntityBuilder withCoverPath(String coverPath) {
    this.coverPath = coverPath;
    return this;
  }

  public CollectionEntityBuilder withType(String type) {
    this.type = type;
    return this;
  }

  public CollectionEntityBuilder withBookEntities(List<BookEntity> bookEntities) {
    this.bookEntities = bookEntities;
    return this;
  }

  public CollectionEntity build() {
    var entity = new CollectionEntity();
    entity.setId(this.id);
    entity.setBookEntities(this.bookEntities);
    entity.setName(this.name);
    entity.setCoverPath(this.coverPath);
    entity.setType(this.type);
    return entity;
  }
}

package tech.mathieu.factories;

import java.util.List;
import tech.mathieu.book.BookEntity;
import tech.mathieu.publisher.PublisherEntity;

public class PublisherEntityBuilder {
  private Long id;
  private String name = "test-publisher";
  private List<BookEntity> bookEntities;

  public PublisherEntityBuilder withId(Long id) {
    this.id = id;
    return this;
  }

  public PublisherEntityBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public PublisherEntityBuilder withBookEntities(List<BookEntity> bookEntities) {
    this.bookEntities = bookEntities;
    return this;
  }

  public PublisherEntity build() {
    var entity = new PublisherEntity();
    entity.setId(this.id);
    entity.setName(this.name);
    entity.setBookEntities(this.bookEntities);
    return entity;
  }
}

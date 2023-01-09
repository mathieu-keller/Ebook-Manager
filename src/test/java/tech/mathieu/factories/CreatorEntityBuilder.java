package tech.mathieu.factories;

import java.util.List;
import tech.mathieu.book.BookEntity;
import tech.mathieu.creator.CreatorEntity;

public class CreatorEntityBuilder {
  private Long id;
  private String name = "test-creator";
  private List<BookEntity> bookEntities;

  public CreatorEntityBuilder withId(Long id) {
    this.id = id;
    return this;
  }

  public CreatorEntityBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public CreatorEntityBuilder withBookEntities(List<BookEntity> bookEntities) {
    this.bookEntities = bookEntities;
    return this;
  }

  public CreatorEntity build() {
    var entity = new CreatorEntity();
    entity.setName(this.name);
    entity.setId(this.id);
    entity.setBookEntities(this.bookEntities);
    return entity;
  }
}

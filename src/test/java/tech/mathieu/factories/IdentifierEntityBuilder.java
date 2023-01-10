package tech.mathieu.factories;

import tech.mathieu.book.BookEntity;
import tech.mathieu.identifier.IdentifierEntity;

public class IdentifierEntityBuilder {
  private Long id;
  private BookEntity bookEntity;
  private String value;
  private String identId;

  public IdentifierEntityBuilder withId(Long id) {
    this.id = id;
    return this;
  }

  public IdentifierEntityBuilder withBookEntity(BookEntity bookEntity) {
    this.bookEntity = bookEntity;
    return this;
  }

  public IdentifierEntityBuilder withValue(String value) {
    this.value = value;
    return this;
  }

  public IdentifierEntityBuilder withIdentId(String identId) {
    this.identId = identId;
    return this;
  }

  public IdentifierEntity build() {
    var entity = new IdentifierEntity();
    entity.setId(this.id);
    entity.setValue(this.value);
    entity.setIdentId(this.identId);
    entity.setBook(this.bookEntity);
    return entity;
  }
}

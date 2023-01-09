package tech.mathieu.factories;

import tech.mathieu.book.BookEntity;
import tech.mathieu.title.TitleEntity;

public class TitleEntityBuilder {
  private Long id;
  private String title = "test-title";
  private String titleType = "main";
  private Long titleOrder = 1L;
  private BookEntity bookEntity;

  public TitleEntityBuilder withId(Long id) {
    this.id = id;
    return this;
  }

  public TitleEntityBuilder withTitle(String title) {
    this.title = title;
    return this;
  }

  public TitleEntityBuilder withTitleType(String titleType) {
    this.titleType = titleType;
    return this;
  }

  public TitleEntityBuilder withTitleOrder(Long titleOrder) {
    this.titleOrder = titleOrder;
    return this;
  }

  public TitleEntityBuilder withBookEntity(BookEntity bookEntity) {
    this.bookEntity = bookEntity;
    return this;
  }

  public TitleEntity build() {
    var entity = new TitleEntity();
    entity.setId(this.id);
    entity.setTitle(this.title);
    entity.setTitleType(this.titleType);
    entity.setTitleOrder(this.titleOrder);
    entity.setBookEntity(this.bookEntity);
    return entity;
  }
}

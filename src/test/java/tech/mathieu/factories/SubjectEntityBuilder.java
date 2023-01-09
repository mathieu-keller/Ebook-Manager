package tech.mathieu.factories;

import java.util.List;
import tech.mathieu.book.BookEntity;
import tech.mathieu.subject.SubjectEntity;

public class SubjectEntityBuilder {
  private Long id;
  private String name = "test-subject";
  private List<BookEntity> bookEntities;

  public SubjectEntityBuilder withId(Long id) {
    this.id = id;
    return this;
  }

  public SubjectEntityBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public SubjectEntityBuilder withBookEntities(List<BookEntity> bookEntities) {
    this.bookEntities = bookEntities;
    return this;
  }

  public SubjectEntity build() {
    var entity = new SubjectEntity();
    entity.setId(this.id);
    entity.setName(this.name);
    entity.setBookEntities(this.bookEntities);
    return entity;
  }
}

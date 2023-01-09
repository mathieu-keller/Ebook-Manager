package tech.mathieu.factories;

import java.util.List;
import tech.mathieu.book.BookEntity;
import tech.mathieu.contributor.ContributorEntity;

public class ContributorEntityBuilder {
  private Long id;
  private String name = "test-contributor";
  private List<BookEntity> bookEntities;

  public ContributorEntityBuilder withId(Long id) {
    this.id = id;
    return this;
  }

  public ContributorEntityBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ContributorEntityBuilder withBookEntities(List<BookEntity> bookEntities) {
    this.bookEntities = bookEntities;
    return this;
  }

  public ContributorEntity build() {
    var entity = new ContributorEntity();
    entity.setId(this.id);
    entity.setName(this.name);
    entity.setBookEntities(this.bookEntities);
    return entity;
  }
}

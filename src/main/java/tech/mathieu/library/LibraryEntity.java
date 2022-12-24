package tech.mathieu.library;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "V_LIBRARY")
public class LibraryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "ID", updatable = false, insertable = false, nullable = false)
  Long id;

  @Column(name = "TITLE", updatable = false, insertable = false, nullable = false)
  String title;

  @Column(name = "ITEM_TYPE", updatable = false, insertable = false, nullable = false)
  String itemType;

  @Column(name = "BOOK_COUNT", updatable = false, insertable = false, nullable = false)
  Long bookCount;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getItemType() {
    return itemType;
  }

  public void setItemType(String itemType) {
    this.itemType = itemType;
  }

  public Long getBookCount() {
    return bookCount;
  }

  public void setBookCount(Long bookCount) {
    this.bookCount = bookCount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof LibraryEntity that)) {
      return false;
    }
    return Objects.equals(id, that.id)
        && Objects.equals(title, that.title)
        && Objects.equals(itemType, that.itemType)
        && Objects.equals(bookCount, that.bookCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, itemType, bookCount);
  }
}

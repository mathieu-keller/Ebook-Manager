package tech.mathieu.library;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
}

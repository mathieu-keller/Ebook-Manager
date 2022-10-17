package tech.mathieu.library;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Immutable
@Table(name = "V_LIBRARY")
public class LibraryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "ID", updatable = false, insertable = false, nullable = false)
  Long id;
  @Lob
  @Column(name = "COVER", updatable = false, insertable = false)
  byte[] cover;
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

  public byte[] getCover() {
    return cover;
  }

  public void setCover(byte[] cover) {
    this.cover = cover;
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
    return Objects.equals(id, that.id) && Arrays.equals(cover, that.cover) && Objects.equals(title, that.title) && Objects.equals(itemType,
        that.itemType) && Objects.equals(bookCount, that.bookCount);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(id, title, itemType, bookCount);
    result = 31 * result + Arrays.hashCode(cover);
    return result;
  }
}

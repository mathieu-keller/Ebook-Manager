package tech.mathieu.identifier;

import tech.mathieu.book.BookEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "identifier")
public class IdentifierEntity {
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Id
  @Column(name = "id", nullable = false)
  int id;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "BOOK_ID")
  BookEntity bookEntity;

  @Column(name = "value", nullable = true, length = 4000)
  String value;

  @Column(name = "IDENT_ID")
  String identId;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public BookEntity getBook() {
    return bookEntity;
  }

  public void setBook(BookEntity bookEntity) {
    this.bookEntity = bookEntity;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String name) {
    this.value = name;
  }

  public String getIdentId() {
    return identId;
  }

  public void setIdentId(String identId) {
    this.identId = identId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof IdentifierEntity that)) {
      return false;
    }
    return id == that.id && Objects.equals(bookEntity, that.bookEntity) && Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, bookEntity, value);
  }
}

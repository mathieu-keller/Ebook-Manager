package tech.mathieu.identifier;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import tech.mathieu.book.BookEntity;

@Entity
@Table(name = "identifier")
public class IdentifierEntity {
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Id
  @Column(name = "id", nullable = false)
  Long id;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "BOOK_ID")
  BookEntity bookEntity;

  @Column(name = "value", nullable = true, length = 4000)
  String value;

  @Column(name = "IDENT_ID")
  String identId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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
}

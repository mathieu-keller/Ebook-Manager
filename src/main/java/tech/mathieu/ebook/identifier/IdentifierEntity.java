package tech.mathieu.ebook.identifier;

import tech.mathieu.ebook.book.BookEntity;

import javax.persistence.Basic;
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
@Table(name = "identifier", schema = "public", catalog = "ebooks")
public class IdentifierEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id", nullable = false)
  private int id;

  @ManyToOne(cascade= CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "BOOK_ID")
  private BookEntity book;

  @Basic
  @Column(name = "name", nullable = true, length = 4000)
  private String name;

  @Column(name = "SCHEMA")
  private String schema;

  @Column(name = "IDENT_ID")
  private String identId;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public BookEntity getBook() {
    return book;
  }

  public void setBook(BookEntity book) {
    this.book = book;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSchema() {
    return schema;
  }

  public void setSchema(String schema) {
    this.schema = schema;
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
    return id == that.id && Objects.equals(book, that.book) && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, book, name);
  }
}

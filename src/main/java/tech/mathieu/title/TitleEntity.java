package tech.mathieu.title;

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
@Table(name = "title")
public class TitleEntity {
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Id
  @Column(name = "id", nullable = false)
  Long id;

  @Column(name = "title", nullable = true, length = 4000)
  String title;

  @Column(name = "TITLE_TYPE", nullable = true, length = 4000)
  String titleType;

  @Column(name = "TITLE_ORDER", nullable = true)
  Long titleOrder;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "BOOK_ID")
  BookEntity bookEntity;

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

  public String getTitleType() {
    return titleType;
  }

  public void setTitleType(String titleType) {
    this.titleType = titleType;
  }

  public Long getTitleOrder() {
    return titleOrder;
  }

  public void setTitleOrder(Long titleOrder) {
    this.titleOrder = titleOrder;
  }

  public BookEntity getBookEntity() {
    return bookEntity;
  }

  public void setBookEntity(BookEntity bookEntity) {
    this.bookEntity = bookEntity;
  }
}

package tech.mathieu.title;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TitleEntity that)) {
      return false;
    }
    return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(titleType, that.titleType) && Objects.equals(titleOrder, that.titleOrder) && Objects.equals(bookEntity, that.bookEntity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, titleType, titleOrder, bookEntity);
  }
}

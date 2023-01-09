package tech.mathieu.language;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import tech.mathieu.book.BookEntity;

@Entity
@Table(name = "language")
public class LanguageEntity {
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Id
  @Column(name = "id", nullable = false)
  Long id;

  @Column(name = "name", nullable = true, length = 4000)
  String name;

  @ManyToMany(targetEntity = BookEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JoinTable(
      name = "language2book",
      joinColumns = @JoinColumn(name = "language_id"),
      inverseJoinColumns = @JoinColumn(name = "BOOK_ID"))
  List<BookEntity> bookEntities;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<BookEntity> getBookEntities() {
    return bookEntities;
  }

  public void setBookEntities(List<BookEntity> bookEntities) {
    this.bookEntities = bookEntities;
  }
}

package tech.mathieu.creator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import tech.mathieu.book.BookEntity;

@Entity
@Table(name = "creator")
public class CreatorEntity {
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Id
  @Column(name = "id", nullable = false)
  Long id;

  @Column(name = "name", nullable = true, length = 4000)
  String name;

  @ManyToMany(targetEntity = BookEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JoinTable(
      name = "CREATOR2BOOK",
      joinColumns = @JoinColumn(name = "CREATOR_ID"),
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

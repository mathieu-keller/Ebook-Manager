package tech.mathieu.collection;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import tech.mathieu.book.BookEntity;

@Table(name = "collection")
@Entity
public class CollectionEntity {

  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Id
  @Column(name = "id", nullable = false)
  Long id;

  @Column(name = "name", nullable = false)
  String name;

  @Column(name = "cover_path", nullable = false)
  String coverPath;

  @Column(name = "type", nullable = true)
  String type;

  @OneToMany(mappedBy = "collectionEntity", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
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

  public String getCoverPath() {
    return coverPath;
  }

  public void setCoverPath(String coverPath) {
    this.coverPath = coverPath;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<BookEntity> getBookEntities() {
    return bookEntities;
  }

  public void setBookEntities(List<BookEntity> bookEntities) {
    this.bookEntities = bookEntities;
  }
}

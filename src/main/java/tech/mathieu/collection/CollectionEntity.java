package tech.mathieu.collection;

import tech.mathieu.book.BookEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Table
@Entity(name = "collection")
public class CollectionEntity {

  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Id
  @Column(name = "id", nullable = false)
  Long id;

  @Column(name = "name", nullable = false)
  String name;

  @Lob
  @Column(name = "cover", nullable = false)
  byte[] cover;

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

  public byte[] getCover() {
    return cover;
  }

  public void setCover(byte[] cover) {
    this.cover = cover;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CollectionEntity that)) {
      return false;
    }
    return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Arrays.equals(cover, that.cover) && Objects.equals(type, that.type) && Objects.equals(bookEntities, that.bookEntities);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(id, name, type, bookEntities);
    result = 31 * result + Arrays.hashCode(cover);
    return result;
  }
}

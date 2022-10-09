package tech.mathieu.ebook.book;

import tech.mathieu.ebook.creator.CreatorEntity;
import tech.mathieu.ebook.identifier.IdentifierEntity;

import javax.persistence.Basic;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book", schema = "public", catalog = "ebooks")
public class BookEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id", nullable = false)
  private int id;

  @Basic
  @Column(name = "title", nullable = true, length = 4000)
  private String title;
  @Basic
  @Column(name = "date", nullable = true, length = 4000)
  private String date;

  @Basic
  @Column(name = "meta", nullable = true, length = 4000)
  private String meta;

  @ManyToMany(
      targetEntity = CreatorEntity.class,
      fetch = FetchType.LAZY
  )
  @JoinTable(
      name = "CREATOR2BOOK",
      joinColumns = @JoinColumn(name = "BOOK_ID"),
      inverseJoinColumns = @JoinColumn(name = "CREATOR_ID")
  )
  private List<CreatorEntity> creatorEntities;

  @OneToMany(cascade= CascadeType.MERGE, fetch = FetchType.LAZY)
  private List<IdentifierEntity> identifierEntities;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getMeta() {
    return meta;
  }

  public void setMeta(String meta) {
    this.meta = meta;
  }

  public List<CreatorEntity> getCreatorEntities() {
    return creatorEntities;
  }

  public void setCreatorEntities(List<CreatorEntity> creatorEntities) {
    this.creatorEntities = creatorEntities;
  }

  public List<IdentifierEntity> getIdentifierEntities() {
    return identifierEntities;
  }

  public void setIdentifierEntities(List<IdentifierEntity> identifierEntities) {
    this.identifierEntities = identifierEntities;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BookEntity that)) {
      return false;
    }
    return id == that.id && Objects.equals(title, that.title) && Objects.equals(date, that.date) && Objects.equals(meta, that.meta) && Objects.equals(creatorEntities, that.creatorEntities) && Objects.equals(identifierEntities, that.identifierEntities);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, date, meta, creatorEntities, identifierEntities);
  }
}

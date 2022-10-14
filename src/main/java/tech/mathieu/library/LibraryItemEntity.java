package tech.mathieu.library;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "V_LIBRARY_ITEM")
public class LibraryItemEntity {

  @Id
  @Column(name = "id")
  Long id;
  @Lob
  @Column(name = "cover")
  byte[] cover;
  @Column(name = "title")
  String title;
  @Column(name = "SEARCH_TERMS")
  String searchTerms;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public byte[] getCover() {
    return cover;
  }

  public void setCover(byte[] cover) {
    this.cover = cover;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSearchTerms() {
    return searchTerms;
  }

  public void setSearchTerms(String searchTerms) {
    this.searchTerms = searchTerms;
  }
}

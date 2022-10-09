package tech.mathieu;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "book", schema = "public", catalog = "ebooks")
public class BookEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id", nullable = false)
  private int id;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Basic
  @Column(name = "title", nullable = true, length = 4000)
  private String title;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Basic
  @Column(name = "creator", nullable = true, length = 4000)
  private String creator;

  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  @Basic
  @Column(name = "contributor", nullable = true, length = 4000)
  private String contributor;

  public String getContributor() {
    return contributor;
  }

  public void setContributor(String contributor) {
    this.contributor = contributor;
  }

  @Basic
  @Column(name = "publisher", nullable = true, length = 4000)
  private String publisher;

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  @Basic
  @Column(name = "identifier", nullable = true, length = 4000)
  private String identifier;

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  @Basic
  @Column(name = "date", nullable = true, length = 4000)
  private String date;

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  @Basic
  @Column(name = "subject", nullable = true, length = 4000)
  private String subject;

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  @Basic
  @Column(name = "languages", nullable = true, length = 4000)
  private String languages;

  public String getLanguages() {
    return languages;
  }

  public void setLanguages(String languages) {
    this.languages = languages;
  }

  @Basic
  @Column(name = "meta", nullable = true, length = 4000)
  private String meta;

  public String getMeta() {
    return meta;
  }

  public void setMeta(String meta) {
    this.meta = meta;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BookEntity that = (BookEntity) o;
    return id == that.id && Objects.equals(title, that.title) && Objects.equals(creator, that.creator) && Objects.equals(contributor,
        that.contributor) && Objects.equals(publisher, that.publisher) && Objects.equals(identifier, that.identifier) && Objects.equals(date,
        that.date) && Objects.equals(subject, that.subject) && Objects.equals(languages, that.languages) && Objects.equals(meta, that.meta);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, creator, contributor, publisher, identifier, date, subject, languages, meta);
  }
}

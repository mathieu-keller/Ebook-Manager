package tech.mathieu.epub.opf;

import tech.mathieu.epub.opf.metadata.Contributor;
import tech.mathieu.epub.opf.metadata.Creator;
import tech.mathieu.epub.opf.metadata.Date;
import tech.mathieu.epub.opf.metadata.Identifier;
import tech.mathieu.epub.opf.metadata.Language;
import tech.mathieu.epub.opf.metadata.Meta;
import tech.mathieu.epub.opf.metadata.Publisher;
import tech.mathieu.epub.opf.metadata.Rights;
import tech.mathieu.epub.opf.metadata.Subject;
import tech.mathieu.epub.opf.metadata.Title;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "titles",
    "creators",
    "contributors",
    "publishers",
    "identifiers",
    "dates",
    "subjects",
    "languages",
    "rights",
    "meta"
})
public class Metadata {


  @XmlElement(name = "title", namespace = "http://purl.org/dc/elements/1.1/")
  private List<Title> titles;

  @XmlElement(name = "creator", namespace = "http://purl.org/dc/elements/1.1/")
  private List<Creator> creators;

  @XmlElement(name = "contributor", namespace = "http://purl.org/dc/elements/1.1/")
  private List<Contributor> contributors;

  @XmlElement(name = "publisher", namespace = "http://purl.org/dc/elements/1.1/")
  private List<Publisher> publishers;

  @XmlElement(name = "identifier", namespace = "http://purl.org/dc/elements/1.1/")
  private List<Identifier> identifiers;

  @XmlElement(name = "date", namespace = "http://purl.org/dc/elements/1.1/")
  private List<Date> dates;

  @XmlElement(name = "subject", namespace = "http://purl.org/dc/elements/1.1/")
  private List<Subject> subjects;

  @XmlElement(name = "language", namespace = "http://purl.org/dc/elements/1.1/")
  private List<Language> languages;

  @XmlElement(name = "rights", namespace = "http://purl.org/dc/elements/1.1/")
  private List<Rights> rights;

  @XmlElement(name = "meta", namespace = "http://www.idpf.org/2007/opf")
  private List<Meta> meta;

  public List<Title> getTitles() {
    return titles;
  }

  public void setTitles(List<Title> titles) {
    this.titles = titles;
  }

  public List<Creator> getCreators() {
    return creators;
  }

  public void setCreators(List<Creator> creators) {
    this.creators = creators;
  }

  public List<Contributor> getContributors() {
    return contributors;
  }

  public void setContributors(List<Contributor> contributors) {
    this.contributors = contributors;
  }

  public List<Publisher> getPublishers() {
    return publishers;
  }

  public void setPublishers(List<Publisher> publishers) {
    this.publishers = publishers;
  }

  public List<Identifier> getIdentifiers() {
    return identifiers;
  }

  public void setIdentifiers(List<Identifier> identifiers) {
    this.identifiers = identifiers;
  }

  public List<Date> getDates() {
    return dates;
  }

  public void setDates(List<Date> dates) {
    this.dates = dates;
  }

  public List<Subject> getSubjects() {
    return subjects;
  }

  public void setSubjects(List<Subject> subjects) {
    this.subjects = subjects;
  }

  public List<Language> getLanguages() {
    return languages;
  }

  public void setLanguages(List<Language> languages) {
    this.languages = languages;
  }

  public List<Rights> getRights() {
    return rights;
  }

  public void setRights(List<Rights> rights) {
    this.rights = rights;
  }

  public List<Meta> getMeta() {
    return meta;
  }

  public void setMeta(List<Meta> meta) {
    this.meta = meta;
  }
}

package tech.mathieu.epub.opf;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.List;
import tech.mathieu.epub.opf.metadata.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "",
    propOrder = {
      "titles",
      "creators",
      "contributors",
      "publishers",
      "identifiers",
      "dates",
      "subjects",
      "languages",
      "rights",
      "meta",
      "descriptions"
    })
public class Metadata {

  @XmlElement(name = "title", namespace = "http://purl.org/dc/elements/1.1/")
  private List<DefaultAttributes> titles;

  @XmlElement(name = "creator", namespace = "http://purl.org/dc/elements/1.1/")
  private List<DefaultAttributes> creators;

  @XmlElement(name = "contributor", namespace = "http://purl.org/dc/elements/1.1/")
  private List<DefaultAttributes> contributors;

  @XmlElement(name = "publisher", namespace = "http://purl.org/dc/elements/1.1/")
  private List<DefaultAttributes> publishers;

  @XmlElement(name = "identifier", namespace = "http://purl.org/dc/elements/1.1/")
  private List<Id> identifiers;

  @XmlElement(name = "date", namespace = "http://purl.org/dc/elements/1.1/")
  private List<Id> dates;

  @XmlElement(name = "subject", namespace = "http://purl.org/dc/elements/1.1/")
  private List<DefaultAttributes> subjects;

  @XmlElement(name = "language", namespace = "http://purl.org/dc/elements/1.1/")
  private List<Id> languages;

  @XmlElement(name = "rights", namespace = "http://purl.org/dc/elements/1.1/")
  private List<DefaultAttributes> rights;

  @XmlElement(name = "meta", namespace = "http://www.idpf.org/2007/opf")
  private List<Meta> meta;

  @XmlElement(name = "description", namespace = "http://purl.org/dc/elements/1.1/")
  private List<DefaultAttributes> descriptions;

  public List<DefaultAttributes> getTitles() {
    return titles;
  }

  public Metadata setTitles(List<DefaultAttributes> titles) {
    this.titles = titles;
    return this;
  }

  public List<DefaultAttributes> getCreators() {
    return creators;
  }

  public Metadata setCreators(List<DefaultAttributes> creators) {
    this.creators = creators;
    return this;
  }

  public List<DefaultAttributes> getContributors() {
    return contributors;
  }

  public Metadata setContributors(List<DefaultAttributes> contributors) {
    this.contributors = contributors;
    return this;
  }

  public List<DefaultAttributes> getPublishers() {
    return publishers;
  }

  public Metadata setPublishers(List<DefaultAttributes> publishers) {
    this.publishers = publishers;
    return this;
  }

  public List<Id> getIdentifiers() {
    return identifiers;
  }

  public Metadata setIdentifiers(List<Id> identifiers) {
    this.identifiers = identifiers;
    return this;
  }

  public List<Id> getDates() {
    return dates;
  }

  public Metadata setDates(List<Id> dates) {
    this.dates = dates;
    return this;
  }

  public List<DefaultAttributes> getSubjects() {
    return subjects;
  }

  public Metadata setSubjects(List<DefaultAttributes> subjects) {
    this.subjects = subjects;
    return this;
  }

  public List<Id> getLanguages() {
    return languages;
  }

  public Metadata setLanguages(List<Id> languages) {
    this.languages = languages;
    return this;
  }

  public List<DefaultAttributes> getRights() {
    return rights;
  }

  public Metadata setRights(List<DefaultAttributes> rights) {
    this.rights = rights;
    return this;
  }

  public List<Meta> getMeta() {
    return meta;
  }

  public Metadata setMeta(List<Meta> meta) {
    this.meta = meta;
    return this;
  }

  public List<DefaultAttributes> getDescriptions() {
    return descriptions;
  }

  public Metadata setDescriptions(List<DefaultAttributes> descriptions) {
    this.descriptions = descriptions;
    return this;
  }
}

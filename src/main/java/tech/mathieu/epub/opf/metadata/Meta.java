package tech.mathieu.epub.opf.metadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "",
    propOrder = {"id", "dir", "refines", "property", "scheme", "lang", "value"})
@XmlRootElement(name = "meta")
public class Meta {
  @XmlAttribute(name = "id")
  private String id;

  @XmlAttribute(name = "dir")
  private String dir;

  @XmlAttribute(name = "refines")
  private String refines;

  @XmlAttribute(name = "property")
  private String property;

  @XmlAttribute(name = "scheme")
  private String scheme;

  @XmlAttribute(name = "lang", namespace = javax.xml.XMLConstants.XML_NS_URI)
  private String lang;

  @XmlValue private String value;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDir() {
    return dir;
  }

  public void setDir(String dir) {
    this.dir = dir;
  }

  public String getRefines() {
    return refines;
  }

  public void setRefines(String refines) {
    this.refines = refines;
  }

  public String getProperty() {
    return property;
  }

  public void setProperty(String property) {
    this.property = property;
  }

  public String getScheme() {
    return scheme;
  }

  public void setScheme(String scheme) {
    this.scheme = scheme;
  }

  public String getLang() {
    return lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "{"
        + "id='"
        + id
        + '\''
        + ", dir='"
        + dir
        + '\''
        + ", refines='"
        + refines
        + '\''
        + ", property='"
        + property
        + '\''
        + ", scheme='"
        + scheme
        + '\''
        + ", lang='"
        + lang
        + '\''
        + ", value='"
        + value
        + '\''
        + '}';
  }
}

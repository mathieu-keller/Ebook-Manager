package tech.mathieu.epub.opf.metadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "name",
    "content"
})
@XmlRootElement(name = "meta")
public class Meta {

  @XmlAttribute(name = "id")
  private String id;
  @XmlAttribute(name = "name")
  private String name;
  @XmlAttribute(name = "content")
  private String content;

  @XmlAttribute(name = "refines")
  private String refines;

  @XmlAttribute(name = "property")
  private String property;

  @XmlAttribute(name = "scheme")
  private String scheme;

  @XmlValue
  private String value;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
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

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}

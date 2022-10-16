package tech.mathieu.epub.opf.metadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "dir",
    "lang",
    "value"
})
@XmlRootElement(name = "title")
public class Title {

  @XmlAttribute(name = "id")
  private String id;
  @XmlAttribute(name = "dir")
  private String dir;
  @XmlAttribute(name = "lang", namespace = javax.xml.XMLConstants.XML_NS_URI)
  private String lang;
  @XmlValue
  private String value;

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
}

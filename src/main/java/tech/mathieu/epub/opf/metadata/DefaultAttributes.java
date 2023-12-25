package tech.mathieu.epub.opf.metadata;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "",
    propOrder = {"dir", "lang"})
public class DefaultAttributes extends Id {
  @XmlAttribute(name = "dir")
  private String dir;

  @XmlAttribute(name = "lang", namespace = javax.xml.XMLConstants.XML_NS_URI)
  private String lang;

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
}

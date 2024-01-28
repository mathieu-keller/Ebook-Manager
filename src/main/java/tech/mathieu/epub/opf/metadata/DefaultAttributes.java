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

  public DefaultAttributes setDir(String dir) {
    this.dir = dir;
    return this;
  }

  public String getLang() {
    return lang;
  }

  public DefaultAttributes setLang(String lang) {
    this.lang = lang;
    return this;
  }

  @Override
  public String getId() {
    return super.getId();
  }

  @Override
  public DefaultAttributes setId(String id) {
    return (DefaultAttributes) super.setId(id);
  }

  @Override
  public String getValue() {
    return super.getValue();
  }

  @Override
  public DefaultAttributes setValue(String value) {
    return (DefaultAttributes) super.setValue(value);
  }
}

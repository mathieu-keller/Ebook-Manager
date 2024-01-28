package tech.mathieu.epub.opf.metadata;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "",
    propOrder = {"id", "value"})
public class Id {
  @XmlAttribute(name = "id")
  private String id;

  @XmlValue private String value;

  public String getId() {
    return id;
  }

  public Id setId(String id) {
    this.id = id;
    return this;
  }

  public String getValue() {
    return value;
  }

  public Id setValue(String value) {
    this.value = value;
    return this;
  }
}

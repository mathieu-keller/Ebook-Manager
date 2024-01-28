package tech.mathieu.epub.opf;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "",
    propOrder = {"id", "href", "mediaType", "properties"})
public class Item {
  @XmlAttribute(name = "id")
  private String id;

  @XmlAttribute(name = "href")
  private String href;

  @XmlAttribute(name = "media-type")
  private String mediaType;

  @XmlAttribute(name = "properties")
  private String properties;

  public String getId() {
    return id;
  }

  public Item setId(String id) {
    this.id = id;
    return this;
  }

  public String getHref() {
    return href;
  }

  public Item setHref(String href) {
    this.href = href;
    return this;
  }

  public String getMediaType() {
    return mediaType;
  }

  public Item setMediaType(String mediaType) {
    this.mediaType = mediaType;
    return this;
  }

  public String getProperties() {
    return properties;
  }

  public Item setProperties(String properties) {
    this.properties = properties;
    return this;
  }
}

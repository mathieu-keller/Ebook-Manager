package tech.mathieu.epub.container;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class Rootfile {
  @XmlAttribute(name = "full-path", required = true)
  private String fullPath;

  @XmlAttribute(name = "media-type", required = true)
  private String mediaType;

  public String getFullPath() {
    return fullPath;
  }

  public void setFullPath(String value) {
    this.fullPath = value;
  }

  public String getMediaType() {
    return mediaType;
  }

  public void setMediaType(String value) {
    this.mediaType = value;
  }
}

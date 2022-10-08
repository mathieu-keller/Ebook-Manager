package tech.mathieu.epub.container;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class Rootfile  {
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

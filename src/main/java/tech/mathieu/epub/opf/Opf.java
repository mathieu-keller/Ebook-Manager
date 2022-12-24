package tech.mathieu.epub.opf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "",
    propOrder = {"metadata", "manifest"})
@XmlRootElement(name = "package")
public class Opf {

  @XmlElement(required = true)
  private Metadata metadata;

  @XmlElement(required = true)
  private Manifest manifest;

  @XmlAttribute(name = "version", required = true)
  private String version;

  @XmlAttribute(name = "unique-identifier", required = true)
  private String uniqueIdentifier;

  @XmlAttribute(name = "dir")
  private String dir;

  @XmlAttribute(name = "lang", namespace = javax.xml.XMLConstants.XML_NS_URI)
  private String lang;

  public Metadata getMetadata() {
    return this.metadata;
  }

  public void setMetadata(Metadata value) {
    this.metadata = value;
  }

  public String getVersion() {
    return this.version;
  }

  public void setVersion(String value) {
    this.version = value;
  }

  public String getUniqueIdentifier() {
    return this.uniqueIdentifier;
  }

  public void setUniqueIdentifier(String value) {
    this.uniqueIdentifier = value;
  }

  public Manifest getManifest() {
    return manifest;
  }

  public void setManifest(Manifest manifest) {
    this.manifest = manifest;
  }
}

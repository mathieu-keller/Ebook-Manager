package tech.mathieu.epub.opf;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import javax.xml.XMLConstants;

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

  @XmlAttribute(name = "lang", namespace = XMLConstants.XML_NS_URI)
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

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
    return metadata;
  }

  public Opf setMetadata(Metadata metadata) {
    this.metadata = metadata;
    return this;
  }

  public Manifest getManifest() {
    return manifest;
  }

  public Opf setManifest(Manifest manifest) {
    this.manifest = manifest;
    return this;
  }

  public String getVersion() {
    return version;
  }

  public Opf setVersion(String version) {
    this.version = version;
    return this;
  }

  public String getUniqueIdentifier() {
    return uniqueIdentifier;
  }

  public Opf setUniqueIdentifier(String uniqueIdentifier) {
    this.uniqueIdentifier = uniqueIdentifier;
    return this;
  }

  public String getDir() {
    return dir;
  }

  public Opf setDir(String dir) {
    this.dir = dir;
    return this;
  }

  public String getLang() {
    return lang;
  }

  public Opf setLang(String lang) {
    this.lang = lang;
    return this;
  }
}

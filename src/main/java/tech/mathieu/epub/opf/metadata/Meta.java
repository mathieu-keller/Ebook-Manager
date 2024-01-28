package tech.mathieu.epub.opf.metadata;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "",
    propOrder = {"name", "content", "refines", "property", "scheme"})
@XmlRootElement(name = "meta")
public class Meta extends DefaultAttributes {

  /**
   * @deprecated it was an attribute in epub2 but is allowed as legacy feature in epub3 see <a
   *     href="https://www.w3.org/publishing/epub3/epub-packages.html#sec-opf2-meta">epub 3 legacy
   *     meta</a>
   */
  @Deprecated(since = "version 3.0")
  @XmlAttribute(name = "name")
  private String name;

  /**
   * @deprecated it was an attribute in epub2 but is allowed as legacy feature in epub3 see <a
   *     href="https://www.w3.org/publishing/epub3/epub-packages.html#sec-opf2-meta">epub 3 legacy
   *     meta</a>
   */
  @Deprecated(since = "version 3.0")
  @XmlAttribute(name = "content")
  private String content;

  @XmlAttribute(name = "refines")
  private String refines;

  @XmlAttribute(name = "property")
  private String property;

  @XmlAttribute(name = "scheme")
  private String scheme;

  public String getName() {
    return name;
  }

  public Meta setName(String name) {
    this.name = name;
    return this;
  }

  public String getContent() {
    return content;
  }

  public Meta setContent(String content) {
    this.content = content;
    return this;
  }

  public String getRefines() {
    return refines;
  }

  public Meta setRefines(String refines) {
    this.refines = refines;
    return this;
  }

  public String getProperty() {
    return property;
  }

  public Meta setProperty(String property) {
    this.property = property;
    return this;
  }

  public String getScheme() {
    return scheme;
  }

  public Meta setScheme(String scheme) {
    this.scheme = scheme;
    return this;
  }
}

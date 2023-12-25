package tech.mathieu.epub.opf.metadata;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "",
    propOrder = { "name", "content", "refines", "property", "scheme"})
@XmlRootElement(name = "meta")
public class Meta extends DefaultAttributes{

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


  /**
   * @deprecated it was an attribute in epub2 but is allowed as legacy feature in epub3 see <a
   *     href="https://www.w3.org/publishing/epub3/epub-packages.html#sec-opf2-meta">epub 3 legacy
   *     meta</a>
   * @return name field
   */
  @Deprecated(since = "version 3.0")
  public String getName() {
    return name;
  }

  /**
   * @deprecated it was an attribute in epub2 but is allowed as legacy feature in epub3 see <a
   *     href="https://www.w3.org/publishing/epub3/epub-packages.html#sec-opf2-meta">epub 3 legacy
   *     meta</a>
   * @param name name
   */
  @Deprecated(since = "version 3.0")
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @deprecated it was an attribute in epub2 but is allowed as legacy feature in epub3 see <a
   *     href="https://www.w3.org/publishing/epub3/epub-packages.html#sec-opf2-meta">epub 3 legacy
   *     meta</a>
   * @return name content
   */
  @Deprecated(since = "version 3.0")
  public String getContent() {
    return content;
  }

  /**
   * @deprecated it was an attribute in epub2 but is allowed as legacy feature in epub3 see <a
   *     href="https://www.w3.org/publishing/epub3/epub-packages.html#sec-opf2-meta">epub 3 legacy
   *     meta</a>
   * @param content content
   */
  @Deprecated(since = "version 3.0")
  public void setContent(String content) {
    this.content = content;
  }

  public String getRefines() {
    return refines;
  }

  public void setRefines(String refines) {
    this.refines = refines;
  }

  public String getProperty() {
    return property;
  }

  public void setProperty(String property) {
    this.property = property;
  }

  public String getScheme() {
    return scheme;
  }

  public void setScheme(String scheme) {
    this.scheme = scheme;
  }
}

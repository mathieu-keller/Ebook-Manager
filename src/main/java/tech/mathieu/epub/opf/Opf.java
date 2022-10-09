package tech.mathieu.epub.opf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "metadata"
})
@XmlRootElement(name = "package")
public class Opf {

  @XmlElement(
      required = true
  )
  private Metadata metadata;

  @XmlAttribute(
      name = "version",
      required = true
  )
  private BigDecimal version;
  @XmlAttribute(
      name = "unique-identifier",
      required = true
  )
  private String uniqueIdentifier;

  public Opf() {
  }

  public Metadata getMetadata() {
    return this.metadata;
  }

  public void setMetadata(Metadata value) {
    this.metadata = value;
  }


  public BigDecimal getVersion() {
    return this.version;
  }

  public void setVersion(BigDecimal value) {
    this.version = value;
  }

  public String getUniqueIdentifier() {
    return this.uniqueIdentifier;
  }

  public void setUniqueIdentifier(String value) {
    this.uniqueIdentifier = value;
  }

}

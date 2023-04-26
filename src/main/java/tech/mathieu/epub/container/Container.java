package tech.mathieu.epub.container;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "",
    propOrder = {"rootfiles"})
@XmlRootElement(name = "container")
public class Container {
  @XmlElement(required = true)
  private Rootfiles rootfiles;

  @XmlAttribute(name = "version", required = true)
  private BigDecimal version;

  public Rootfiles getRootfiles() {
    return rootfiles;
  }

  public void setRootfiles(Rootfiles value) {
    this.rootfiles = value;
  }

  public BigDecimal getVersion() {
    return version;
  }

  public void setVersion(BigDecimal value) {
    this.version = value;
  }
}

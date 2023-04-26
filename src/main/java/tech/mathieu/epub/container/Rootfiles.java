package tech.mathieu.epub.container;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "",
    propOrder = {"rootfile"})
public class Rootfiles {
  @XmlElement(required = true)
  private Rootfile[] rootfile;

  public Rootfile[] getRootfile() {
    return rootfile;
  }

  public void setRootfile(Rootfile[] value) {
    this.rootfile = value;
  }
}

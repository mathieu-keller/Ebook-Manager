package tech.mathieu.epub.container;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

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

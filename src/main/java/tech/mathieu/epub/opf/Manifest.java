package tech.mathieu.epub.opf;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "",
    propOrder = {"items"})
public class Manifest {

  @XmlElement(name = "item", namespace = "http://www.idpf.org/2007/opf")
  private List<Item> items;

  public List<Item> getItems() {
    return items;
  }

  public Manifest setItems(List<Item> items) {
    this.items = items;
    return this;
  }
}

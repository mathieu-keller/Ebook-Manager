package tech.mathieu.epub;

import tech.mathieu.epub.container.Container;
import tech.mathieu.epub.opf.Opf;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.JAXB;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

@ApplicationScoped
public class Reader {

  public Opf read(InputStream ebook) throws IOException {
    var book = ebook.readAllBytes();
    var opfPath= getOpfPath(new ByteArrayInputStream(book));
    return getPackage(new ByteArrayInputStream(book), opfPath);
  }

  private Opf getPackage(InputStream ebook, String opfPath) throws IOException {
    Opf pa =null;
    try (var zipIn = new ZipInputStream(ebook)) {
      for (var zipEntry = zipIn.getNextEntry(); zipEntry != null; zipEntry = zipIn.getNextEntry()) {
        if (opfPath.equals(zipEntry.getName())) {
          pa = JAXB.unmarshal(new ByteArrayInputStream(zipIn.readAllBytes()), Opf.class);
          break;
        }
      }
    }
    return pa;
  }

  private String getOpfPath(InputStream ebook) throws IOException {
    String opfPath = null;
    try (var zipIn = new ZipInputStream(ebook)) {
      for (var zipEntry = zipIn.getNextEntry(); zipEntry != null; zipEntry = zipIn.getNextEntry()) {
        if ("META-INF/container.xml".equals(zipEntry.getName())) {
          var container = JAXB.unmarshal(new ByteArrayInputStream(zipIn.readAllBytes()), Container.class);
          opfPath = container.getRootfiles().getRootfile()[0].getFullPath();
          break;
        }
      }
    }
    return opfPath;
  }

}

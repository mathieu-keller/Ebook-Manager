package tech.mathieu.epub;

import tech.mathieu.epub.container.Container;
import tech.mathieu.epub.opf.Package;

import javax.xml.bind.JAXB;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

public class Reader {

  public Package read(InputStream ebook) throws IOException {
    var book = ebook.readAllBytes();
    var opfPath= getOpfPath(new ByteArrayInputStream(book));
    return getPackage(new ByteArrayInputStream(book), opfPath);
  }

  private Package getPackage(InputStream ebook, String opfPath) throws IOException {
    Package pa =null;
    try (var zipIn = new ZipInputStream(ebook)) {
      for (var zipEntry = zipIn.getNextEntry(); zipEntry != null; zipEntry = zipIn.getNextEntry()) {
        if (opfPath.equals(zipEntry.getName())) {
          pa = JAXB.unmarshal(new ByteArrayInputStream(zipIn.readAllBytes()), Package.class);
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

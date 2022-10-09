package tech.mathieu.epub;

import tech.mathieu.epub.container.Container;
import tech.mathieu.epub.opf.Item;
import tech.mathieu.epub.opf.Opf;
import tech.mathieu.epub.opf.metadata.Meta;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.JAXB;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@ApplicationScoped
public class Reader {

  public Epub read(InputStream ebook) throws IOException {
    var book = ebook.readAllBytes();
    String opfPath;
    try (var in = new ByteArrayInputStream(book)) {
      opfPath = getOpfPath(in);
    }
    Opf opf;
    try (var in = new ByteArrayInputStream(book)) {
      opf = getPackage(in, opfPath);
    }
    byte[] cover;
    try (var in = new ByteArrayInputStream(book)) {
      cover = getCover(opf, opfPath, in);
    }
    return new Epub(opf, cover);
  }

  private byte[] getCover(Opf opf, String opfPath, InputStream ebook) throws IOException {
    var path = getCoverPath(opf);
    if (path == null) {
      return null;
    }
    try (var zipIn = new ZipInputStream(ebook)) {
      ZipEntry zipEntry;
      while ((zipEntry = zipIn.getNextEntry()) != null) {
        var rootFolder = getRootPath(opfPath);
        if (path.equals(zipEntry.getName()) || (rootFolder + "/" + path).equals(zipEntry.getName())) {
          return Base64.getEncoder().encode(zipIn.readAllBytes());
        }
      }
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw e;
    }
    return null;
  }

  private String getRootPath(String opfPath) {
    return Arrays.stream(opfPath.split("/"))
        .filter(element -> !element.endsWith(".opf"))
        .collect(Collectors.joining("/"));
  }

  private String getCoverPath(Opf epub) {
    if (epub.getMetadata().getMeta() == null) {
      return null;
    }
    var coverPath = epub.getMetadata().getMeta()
        .stream()
        .filter(meta -> "cover".equalsIgnoreCase(meta.getName()))
        .map(Meta::getContent)
        .map(id -> epub.getManifest().getItems().stream().filter(item -> id.equalsIgnoreCase(item.getId())).findFirst())
        .filter(Optional::isPresent)
        .map(Optional::get)
        .map(Item::getHref)
        .findFirst();
    return coverPath.orElse(null);
  }

  private Opf getPackage(InputStream ebook, String opfPath) throws IOException {
    Opf pa = null;
    try (var zipIn = new ZipInputStream(ebook)) {
      ZipEntry zipEntry;
      while ((zipEntry = zipIn.getNextEntry()) != null) {
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
      ZipEntry zipEntry;
      while ((zipEntry = zipIn.getNextEntry()) != null) {
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

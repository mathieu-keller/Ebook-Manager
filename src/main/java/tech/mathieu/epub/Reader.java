package tech.mathieu.epub;

import net.coobird.thumbnailator.Thumbnails;
import tech.mathieu.epub.container.Container;
import tech.mathieu.epub.opf.Item;
import tech.mathieu.epub.opf.Opf;
import tech.mathieu.epub.opf.metadata.Meta;

import javax.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;
import javax.xml.bind.JAXB;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;

@ApplicationScoped
public class Reader {

  public Epub read(ZipFile zipFile) throws IOException {
    var opfPath = getOpfPath(zipFile);
    if (opfPath == null) {
      return null;
    }
    var opf = getPackage(zipFile, opfPath);
    if (opf == null) {
      return null;
    }
    var cover = getCover(opf, opfPath, zipFile);
    return new Epub(opf, cover);
  }

  private double determineImageScale(int sourceWidth, int targetWidth) {
    return (double) targetWidth / sourceWidth;
  }

  byte[] resizeImage(BufferedImage originalImage, int targetWidth) throws IOException {
    var scale = determineImageScale(originalImage.getWidth(), targetWidth);
    var calculatedWidth = (int) (originalImage.getWidth() * scale);
    var calculatedHeight = (int) (originalImage.getHeight() * scale);
    try (var baos = new ByteArrayOutputStream()) {
      Thumbnails.of(originalImage)
          .size(calculatedWidth, calculatedHeight)
          .outputFormat("JPEG")
          .outputQuality(0.5f)
          .toOutputStream(baos);
      return Base64.getEncoder().encode(baos.toByteArray());
    }
  }

  private byte[] getCover(Opf opf, String opfPath, ZipFile zipFile) throws IOException {
    var path = getCoverPath(opf);
    if (path == null) {
      return null;
    }
    var zipEntry = zipFile.getEntry(path);
    if (zipEntry == null) {
      var rootFolder = getRootPath(opfPath);
      zipEntry = zipFile.getEntry(rootFolder + "/" + path);
    }
    if (zipEntry != null) {
      try (var zipIn = zipFile.getInputStream(zipEntry)) {
        return resizeImage(ImageIO.read(zipIn), 270);
      }
    }
    return null;
  }

  private String getRootPath(String opfPath) {
    return Arrays.stream(opfPath.split("/"))
        .filter(element -> !element.endsWith(".opf"))
        .collect(Collectors.joining("/"));
  }

  private String getCoverPath(Opf epub) {
    if (epub.getMetadata().getMeta() == null || epub.getManifest().getItems() == null) {
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

  private Opf getPackage(ZipFile zipFile, String opfPath) throws IOException {
    try (var zipIn = zipFile.getInputStream(zipFile.getEntry(opfPath))) {
      return JAXB.unmarshal(new ByteArrayInputStream(zipIn.readAllBytes()), Opf.class);
    }
  }

  private String getOpfPath(ZipFile zipFile) throws IOException {
    try (var zipIn = zipFile.getInputStream(zipFile.getEntry("META-INF/container.xml"))) {
      var container = JAXB.unmarshal(new ByteArrayInputStream(zipIn.readAllBytes()), Container.class);
      return container.getRootfiles().getRootfile()[0].getFullPath();
    }
  }

}

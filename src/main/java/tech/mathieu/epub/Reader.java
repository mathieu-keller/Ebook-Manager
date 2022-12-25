package tech.mathieu.epub;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.ZipFile;
import javax.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;
import javax.xml.bind.JAXB;
import net.coobird.thumbnailator.Thumbnails;
import tech.mathieu.epub.container.Container;
import tech.mathieu.epub.opf.Item;
import tech.mathieu.epub.opf.Opf;

@ApplicationScoped
public class Reader {

  public Opf read(ZipFile zipFile) throws IOException {
    var opfPath = getOpfPath(zipFile);
    if (opfPath == null) {
      return null;
    }
    return getPackage(zipFile, opfPath);
  }

  private double determineImageScale(int sourceWidth, int targetWidth) {
    return (double) targetWidth / sourceWidth;
  }

  void resizeImage(BufferedImage originalImage, String savePath) throws IOException {
    var scale = determineImageScale(originalImage.getWidth(), 300);
    var calculatedWidth = (int) (originalImage.getWidth() * scale);
    var calculatedHeight = (int) (originalImage.getHeight() * scale);
    Thumbnails.of(originalImage)
        .size(calculatedWidth, calculatedHeight)
        .outputQuality(0.7f)
        .outputFormat("JPEG")
        .toFile(savePath + "/cover.jpeg");
  }

  public void saveCover(Opf opf, ZipFile zipFile, String savePath) throws IOException {
    var path = getCoverPath(opf);
    if (path != null) {
      var zipEntry = zipFile.getEntry(path);
      if (zipEntry != null) {
        try (var zipIn = zipFile.getInputStream(zipEntry)) {
          resizeImage(ImageIO.read(zipIn), savePath);
        }
      }
    }
  }

  private String getCoverPath(Opf opf) {
    return opf.getManifest().getItems().stream()
        .filter(item -> item.getProperties() != null)
        .filter(item -> item.getProperties().contains("cover-image"))
        .map(Item::getHref)
        .findFirst()
        .orElse(null);
  }

  private Opf getPackage(ZipFile zipFile, String opfPath) throws IOException {
    try (var zipIn = zipFile.getInputStream(zipFile.getEntry(opfPath))) {
      return JAXB.unmarshal(new ByteArrayInputStream(zipIn.readAllBytes()), Opf.class);
    }
  }

  private String getOpfPath(ZipFile zipFile) throws IOException {
    try (var zipIn = zipFile.getInputStream(zipFile.getEntry("META-INF/container.xml"))) {
      var container =
          JAXB.unmarshal(new ByteArrayInputStream(zipIn.readAllBytes()), Container.class);
      return container.getRootfiles().getRootfile()[0].getFullPath();
    }
  }
}

package tech.mathieu.epub;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.JAXB;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import tech.mathieu.epub.container.Container;
import tech.mathieu.epub.opf.Item;
import tech.mathieu.epub.opf.Opf;
import tech.mathieu.epub.opf.metadata.Meta;
import tech.mathieu.util.Pair;

@ApplicationScoped
public class Reader {

  public Pair<String, Opf> read(ZipFile zipFile) throws IOException {
    var opfPath = getOpfPath(zipFile);
    if (opfPath == null) {
      return null;
    }
    return getPackage(zipFile, opfPath);
  }

  private double determineImageScale(int sourceWidth, int targetWidth) {
    return (double) targetWidth / sourceWidth;
  }

  private void resizeImage(BufferedImage img, String savePath) throws IOException {
    var scale = determineImageScale(img.getWidth(), 540);
    var width = (int) (img.getWidth() * scale);
    var height = (int) (img.getHeight() * scale);

    BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics2D = resizedImage.createGraphics();
    graphics2D.drawImage(img, 0, 0, width, height, null);
    graphics2D.dispose();
    final FileOutputStream bos = new FileOutputStream(savePath);
    ImageIO.write(resizedImage, "JPEG", bos);
  }

  public void saveCover(Pair<String, Opf> opfWithPath, ZipFile zipFile, String savePath)
      throws IOException {
    var path = getCoverPath(opfWithPath.right());
    if (path != null) {
      var zipEntry = zipFile.getEntry(path);
      if (zipEntry == null) {
        var opfFolder = Paths.get(opfWithPath.left()).getParent().toString();
        zipEntry = zipFile.getEntry(opfFolder + "/" + path);
      }
      if (zipEntry != null) {
        try (var zipIn = zipFile.getInputStream(zipEntry)) {
          resizeImage(ImageIO.read(zipIn), savePath);
        }
      }
    }
  }

  // visible for testing
  String getCoverPath(Opf opf) {
    // epub 3 way to get cover
    var coverPath =
        opf.getManifest().getItems().stream()
            .filter(item -> item.getProperties() != null)
            .filter(item -> item.getProperties().contains("cover-image"))
            .map(Item::getHref)
            .findFirst()
            .orElse(null);
    if (coverPath == null) {
      // epub 2 way but used sometimes used in epub 3 because this way is only deprecated and not
      // removed
      coverPath =
          opf.getMetadata().getMeta().stream()
              .filter(meta -> "cover".equalsIgnoreCase(meta.getName()))
              .map(Meta::getContent)
              .map(
                  id ->
                      opf.getManifest().getItems().stream()
                          .filter(item -> id.equalsIgnoreCase(item.getId()))
                          .findFirst())
              .filter(Optional::isPresent)
              .map(Optional::get)
              .map(Item::getHref)
              .findFirst()
              .orElse(null);
    }
    return coverPath;
  }

  private Pair<String, Opf> getPackage(ZipFile zipFile, String opfPath) throws IOException {
    try (var zipIn = zipFile.getInputStream(zipFile.getEntry(opfPath))) {
      var opf = JAXB.unmarshal(new ByteArrayInputStream(zipIn.readAllBytes()), Opf.class);
      if (opf.getVersion().equals("3.0")) {
        return Pair.of(opfPath, opf);
      }
      throw new IllegalArgumentException("Epub version " + opf.getVersion() + " is not supported!");
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

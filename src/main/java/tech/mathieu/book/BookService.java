package tech.mathieu.book;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;
import tech.mathieu.epub.Reader;
import tech.mathieu.epub.opf.Opf;
import tech.mathieu.epub.opf.metadata.Id;
import tech.mathieu.epub.opf.metadata.Meta;

@ApplicationScoped
public class BookService {

  private final Reader reader;

  public BookService(Reader reader) {
    this.reader = reader;
  }

  // visible for test
  boolean isArchive(File file) throws IOException {
    try (var raf = new RandomAccessFile(file, "r")) {
      return switch (raf.readInt()) {
        case 0x504B0304, 0x504B0506, 0x504B0708 -> true;
        default -> false;
      };
    }
  }

  public Uni<Book> processInbox(File file) {
    try {
      if (!isArchive(file)) {
        throw new IllegalArgumentException("is not an epub file!");
      }
      ZipFile zipFile = new ZipFile(file);
      var opfWithPath = reader.read(zipFile);
      return saveBook(opfWithPath.right());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  Uni<Book> saveBook(Opf opf) {
    return getGetBook(opf).persistOrUpdate();
  }

  Book getGetBook(Opf opf) {
    var meta = opf.getMetadata().getMeta();
    Map<String, Map<String, Meta>> metadata = new HashMap<>();
    if (meta != null) {
      metadata =
          opf.getMetadata().getMeta().stream()
              .filter(m -> m.getRefines() != null)
              .collect(
                  Collectors.groupingBy(
                      Meta::getRefines, Collectors.toMap(Meta::getProperty, Function.identity())));
    }
    return new Book().setId(getBookId(opf)).setTitles(getTitle(opf, metadata));
  }

  List<Title> getTitle(Opf opf, Map<String, Map<String, Meta>> metadata) {
    var epubTitles = opf.getMetadata().getTitles();
    if (epubTitles == null || epubTitles.isEmpty()) {
      throw new IllegalArgumentException("No Title found!");
    }
    var returnList = new ArrayList<Title>();
    for (var epubTitle : epubTitles) {
      if (epubTitle.getId() != null) {
        var id = "#" + epubTitle.getId();
        if (metadata.containsKey(id)) {
          var meta = metadata.get(id);
          var title = meta.getOrDefault("title-type", new Meta()).getValue();
          var displaySeq = meta.getOrDefault("display-seq", new Meta()).getValue();
          returnList.add(
              new Title(
                  epubTitle.getValue(),
                  title == null ? "main" : title,
                  displaySeq == null ? "0" : displaySeq,
                  meta.getOrDefault("lang", new Meta()).getValue()));
        } else {
          returnList.add(new Title(epubTitle.getValue(), "main", "0", null));
        }
      } else {
        returnList.add(new Title(epubTitle.getValue(), "main", "0", null));
      }
    }
    return returnList;
  }

  // visible for test
  String getBookId(Opf opf) {
    return opf.getMetadata().getIdentifiers().stream()
        .filter(identifier -> Objects.equals(identifier.getId(), opf.getUniqueIdentifier()))
        .map(Id::getValue)
        .reduce(
            (first, second) -> {
              throw new IllegalArgumentException("Too many main Identifiers found!");
            })
        .orElseThrow(() -> new IllegalArgumentException("No main Identifiers found!"));
  }
}

package tech.mathieu.book;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.*;
import java.util.Objects;
import java.util.zip.ZipFile;
import tech.mathieu.epub.Reader;
import tech.mathieu.epub.opf.Opf;
import tech.mathieu.epub.opf.metadata.Id;

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
    Book result = getGetBook(opf);
    return result.persistOrUpdate();
  }

  private Book getGetBook(Opf opf) {
    var book = new Book();
    book.id = getBookId(opf);
    return book;
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

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
    var fileSignature = 0;
    try (var raf = new RandomAccessFile(file, "r")) {
      fileSignature = raf.readInt();
    }
    return fileSignature == 0x504B0304
        || fileSignature == 0x504B0506
        || fileSignature == 0x504B0708;
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
    String idName = opf.getUniqueIdentifier();
    var id =
        opf.getMetadata().getIdentifiers().stream()
            .filter(identifier -> Objects.equals(identifier.getId(), idName))
            .map(Id::getValue)
            .toList();
    if (id.isEmpty()) {
      throw new IllegalArgumentException("No main Identifiers found!");
    }
    if (id.size() != 1) {
      throw new IllegalArgumentException("Too many main Identifiers found!");
    }
    return id.getFirst();
  }
}

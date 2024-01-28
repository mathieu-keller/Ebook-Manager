package tech.mathieu.book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import io.quarkus.test.junit.QuarkusTest;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;
import tech.mathieu.epub.opf.Metadata;
import tech.mathieu.epub.opf.Opf;
import tech.mathieu.epub.opf.metadata.Id;

@QuarkusTest
class BookServiceTest {

  private final BookService bookService;

  public BookServiceTest(BookService bookService) {
    this.bookService = bookService;
  }

  @Test
  void shouldReturnTrueWhenFileIsZipArchive() throws IOException {
    Path zipPath = Paths.get("src/test/resources/epub/3.0/TestBook.epub");
    boolean result = bookService.isArchive(zipPath.toFile());
    assertThat(result).isTrue();
  }

  @Test
  void shouldReturnFalseWhenFileIsNotZipArchive() throws IOException {
    Path txtPath = Paths.get("src/test/resources/epub/FakeEbook.epub");
    boolean result = bookService.isArchive(txtPath.toFile());
    assertThat(result).isFalse();
  }

  @Test
  void shouldReturnBookIdWhenSingleIdentifierMatches() throws Exception {
    var opf = new Opf();
    opf.setUniqueIdentifier("uniqueId");
    var metaData = new Metadata();
    var correctId = new Id();
    correctId.setId("uniqueId");
    correctId.setValue("expectedValue");
    var wrongId = new Id();
    wrongId.setId("wrongId");
    wrongId.setValue("wrongIdValue");
    var noId = new Id();
    noId.setId(null);
    noId.setValue("noIdValue");
    metaData.setIdentifiers(List.of(correctId, wrongId, noId));
    opf.setMetadata(metaData);

    var actual = bookService.getBookId(opf);

    assertThat(actual).isEqualTo("expectedValue");
  }

  @Test
  void shouldThrowExceptionWhenNoIdentifiersMatch() throws Exception {
    var opf = new Opf();
    opf.setUniqueIdentifier("uniqueId");
    var metaData = new Metadata();
    metaData.setIdentifiers(List.of());
    opf.setMetadata(metaData);

    assertThrows(IllegalArgumentException.class, () -> bookService.getBookId(opf));
  }

  @Test
  void shouldThrowExceptionWhenMultipleIdentifiersMatch() throws Exception {
    var opf = new Opf();
    opf.setUniqueIdentifier("uniqueId");
    var metaData = new Metadata();
    var correctId = new Id();
    correctId.setId("uniqueId");
    correctId.setValue("expectedValue");
    var wrongId = new Id();
    wrongId.setId("uniqueId");
    wrongId.setValue("expectedValue");
    metaData.setIdentifiers(List.of(correctId, wrongId));
    opf.setMetadata(metaData);

    assertThrows(IllegalArgumentException.class, () -> bookService.getBookId(opf));
  }
}

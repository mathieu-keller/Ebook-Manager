package tech.mathieu.book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Test;
import tech.mathieu.epub.opf.Metadata;
import tech.mathieu.epub.opf.Opf;
import tech.mathieu.epub.opf.metadata.DefaultAttributes;
import tech.mathieu.epub.opf.metadata.Id;

@QuarkusTest
class BookServiceTest {

  private final BookService bookService;

  public BookServiceTest(BookService bookService) {
    this.bookService = bookService;
  }

  @Test
  void shouldProcessInboxWhenFileIsEpub() {
    File epubFile = new File("src/test/resources/epub/3.0/TestBook.epub");
    Book expectedBook =
        new Book()
            .setId("urn:isbn:0001112223334")
            .setTitle("Test Book 01")
            .setSubjects(List.of("Action", "Fantasy", "Manga", "Mythology", "School", "Shounen"));
    Uni<Book> result = bookService.processInbox(epubFile);
    assertThat(result.await().atMost(Duration.ofSeconds(1))).isEqualTo(expectedBook);
  }

  @Test
  void shouldThrowExceptionWhenFileIsNotEpub() {
    File notEpubFile = new File("src/test/resources/epub/FakeEbook.epub");

    assertThrows(IllegalArgumentException.class, () -> bookService.processInbox(notEpubFile));
  }

  @Test
  void shouldThrowExceptionWhenFileDoesNotExist() {
    File nonExistentFile = new File("src/test/resources/nonexistent.file");

    assertThrows(RuntimeException.class, () -> bookService.processInbox(nonExistentFile));
  }

  @Test
  void shouldReturnBookWhenSaveBookIsCalled() {
    Opf opf =
        new Opf()
            .setUniqueIdentifier("uniqueId")
            .setMetadata(
                new Metadata()
                    .setIdentifiers(
                        List.of(
                            new Id().setId("uniqueId").setValue("expectedValue"),
                            new Id().setId("wrongId").setValue("wrongIdValue"),
                            new Id().setId(null).setValue("noIdValue")))
                    .setTitles(
                        List.of(
                            new DefaultAttributes().setValue("Title 1"),
                            new DefaultAttributes().setValue("Title 2"))));

    Book expectedBook = new Book().setId("expectedValue").setTitle("Title 1");

    Uni<Book> result = bookService.saveBook(opf);

    assertThat(result.await().atMost(Duration.ofSeconds(1))).isEqualTo(expectedBook);
  }

  @Test
  void shouldReturnBookWhenGetBookIsCalled() {
    Opf opf =
        new Opf()
            .setUniqueIdentifier("uniqueId")
            .setMetadata(
                new Metadata()
                    .setIdentifiers(
                        List.of(
                            new Id().setId("uniqueId").setValue("expectedValue"),
                            new Id().setId("wrongId").setValue("wrongIdValue"),
                            new Id().setId(null).setValue("noIdValue")))
                    .setTitles(
                        List.of(
                            new DefaultAttributes().setValue("Title 1"),
                            new DefaultAttributes().setValue("Title 2"))));

    Book expectedBook = new Book().setId("expectedValue").setTitle("Title 1");

    Book result = bookService.getGetBook(opf);

    assertThat(result).isEqualTo(expectedBook);
  }

  @Test
  void shouldReturnTitleWhenGetTitleIsCalled() {
    Opf opf =
        new Opf()
            .setMetadata(
                new Metadata()
                    .setTitles(
                        List.of(
                            new DefaultAttributes().setValue("Title 1"),
                            new DefaultAttributes().setValue("Title 2"))));

    var result = bookService.getTitle(opf);
    assertThat(result).isEqualTo("Title 1");
  }

  @Test
  void shouldThrowExceptionWhenTitlesAreNull() {
    Opf opf = new Opf().setMetadata(new Metadata().setTitles(null));

    assertThrows(IllegalArgumentException.class, () -> bookService.getTitle(opf));
  }

  @Test
  void shouldThrowExceptionWhenNoTitlesArePresent() {
    Opf opf = new Opf().setMetadata(new Metadata().setTitles(List.of()));

    assertThrows(IllegalArgumentException.class, () -> bookService.getTitle(opf));
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
    Opf opf =
        new Opf()
            .setUniqueIdentifier("uniqueId")
            .setMetadata(
                new Metadata()
                    .setIdentifiers(
                        List.of(
                            new Id().setId("uniqueId").setValue("expectedValue"),
                            new Id().setId("wrongId").setValue("wrongIdValue"),
                            new Id().setId(null).setValue("noIdValue")))
                    .setTitles(
                        List.of(
                            new DefaultAttributes().setValue("Title 1"),
                            new DefaultAttributes().setValue("Title 2"))));

    var actual = bookService.getBookId(opf);

    assertThat(actual).isEqualTo("expectedValue");
  }

  @Test
  void shouldThrowExceptionWhenNoIdentifiersMatch() throws Exception {
    Opf opf =
        new Opf()
            .setUniqueIdentifier("uniqueId")
            .setMetadata(
                new Metadata()
                    .setIdentifiers(List.of())
                    .setTitles(
                        List.of(
                            new DefaultAttributes().setValue("Title 1"),
                            new DefaultAttributes().setValue("Title 2"))));

    assertThrows(IllegalArgumentException.class, () -> bookService.getBookId(opf));
  }

  @Test
  void shouldThrowExceptionWhenMultipleIdentifiersMatch() throws Exception {
    Opf opf =
        new Opf()
            .setUniqueIdentifier("uniqueId")
            .setMetadata(
                new Metadata()
                    .setIdentifiers(
                        List.of(
                            new Id().setId("uniqueId").setValue("expectedValue"),
                            new Id().setId("uniqueId").setValue("wrongIdValue"),
                            new Id().setId(null).setValue("noIdValue")))
                    .setTitles(
                        List.of(
                            new DefaultAttributes().setValue("Title 1"),
                            new DefaultAttributes().setValue("Title 2"))));

    assertThrows(IllegalArgumentException.class, () -> bookService.getBookId(opf));
  }
}

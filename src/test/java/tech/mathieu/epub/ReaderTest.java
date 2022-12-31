package tech.mathieu.epub;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.quarkus.test.junit.QuarkusTest;
import java.io.File;
import java.util.List;
import java.util.zip.ZipFile;
import javax.inject.Inject;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import tech.mathieu.epub.opf.Item;
import tech.mathieu.epub.opf.Manifest;
import tech.mathieu.epub.opf.Metadata;
import tech.mathieu.epub.opf.Opf;
import tech.mathieu.epub.opf.metadata.Meta;
import tech.mathieu.epub.opf.metadata.Subject;

@QuarkusTest
class ReaderTest {

  @Inject Reader reader;

  @Test
  void test_parse_ebook() throws Exception {
    var testFile = new File(getClass().getResource("/epub/3.0/TestBook.epub").toURI());
    try (var testZipFile = new ZipFile(testFile)) {
      var book = reader.read(testZipFile).right();

      SoftAssertions.assertSoftly(
          softly -> {
            // version
            softly.assertThat(book.getVersion()).isEqualTo("3.0");
            // title
            softly.assertThat(book.getMetadata().getTitles()).hasSize(3);
            var bookTitle =
                book.getMetadata().getTitles().stream()
                    .filter(title -> title.getId().equals("t1"))
                    .findFirst();
            var bookSubTitle =
                book.getMetadata().getTitles().stream()
                    .filter(title -> title.getId().equals("t2"))
                    .findFirst();
            var bookCollectionTitle =
                book.getMetadata().getTitles().stream()
                    .filter(title -> title.getId().equals("t3"))
                    .findFirst();
            softly.assertThat(bookTitle).isPresent();
            softly.assertThat(bookSubTitle).isPresent();
            softly.assertThat(bookCollectionTitle).isPresent();
            softly.assertThat(bookTitle.get().getValue()).contains("Test Book 01");
            softly.assertThat(bookTitle.get().getLang()).contains("de");
            softly.assertThat(bookSubTitle.get().getValue()).contains("-");
            softly.assertThat(bookSubTitle.get().getLang()).contains("de");
            softly.assertThat(bookCollectionTitle.get().getValue()).contains("Test Books");
            softly.assertThat(bookCollectionTitle.get().getLang()).contains("de");

            // creators
            softly.assertThat(book.getMetadata().getCreators()).hasSize(1);
            softly
                .assertThat(book.getMetadata().getCreators().get(0).getValue())
                .isEqualTo("Last Name, First Name");
            softly
                .assertThat(book.getMetadata().getCreators().get(0).getId())
                .isEqualTo("creator0");

            // contributors
            softly.assertThat(book.getMetadata().getContributors()).isNull();

            // publishers
            softly.assertThat(book.getMetadata().getPublishers()).hasSize(1);
            softly
                .assertThat(book.getMetadata().getPublishers().get(0).getValue())
                .isEqualTo("Test Publisher");

            // identifier
            softly.assertThat(book.getMetadata().getIdentifiers()).hasSize(1);
            softly
                .assertThat(book.getMetadata().getIdentifiers().get(0).getId())
                .isEqualTo("p1234");
            softly
                .assertThat(book.getMetadata().getIdentifiers().get(0).getValue())
                .isEqualTo("urn:isbn:0001112223334");

            // dates
            softly.assertThat(book.getMetadata().getDates()).hasSize(1);
            softly
                .assertThat(book.getMetadata().getDates().get(0).getValue())
                .isEqualTo("2020-03-04");

            // subjects
            var subjects =
                book.getMetadata().getSubjects().stream().map(Subject::getValue).toList();
            softly
                .assertThat(subjects)
                .containsExactly("Action", "Fantasy", "Manga", "Mythology", "School", "Shounen");

            // languages
            softly.assertThat(book.getMetadata().getLanguages()).hasSize(1);
            softly.assertThat(book.getMetadata().getLanguages().get(0).getValue()).isEqualTo("de");

            // meta
            softly.assertThat(book.getMetadata().getMeta()).hasSize(8);
          });
    }
  }

  @Test
  void test_parse_ebook_2_0_expect_to_get_an_illegal_argument_exception() throws Exception {
    var testFile = new File(getClass().getResource("/epub/2.0/TestBook.epub").toURI());
    try (var testZipFile = new ZipFile(testFile)) {
      assertThrows(IllegalArgumentException.class, () -> reader.read(testZipFile));
    }
  }

  @Test
  void test_get_cover_path_with_the_epub3_way() {
    var opf = new Opf();
    var manifest = new Manifest();
    var coverItem = new Item();
    var otherItem1 = new Item();
    var otherItem2 = new Item();
    coverItem.setProperties("cover-image");
    coverItem.setHref("cover-path");
    manifest.setItems(List.of(otherItem1, coverItem, otherItem2));
    opf.setManifest(manifest);
    var path = reader.getCoverPath(opf);
    assertThat(path).isEqualTo("cover-path");
  }

  @Test
  void test_get_cover_path_with_the_epub2_way() {
    var opf = new Opf();
    var metaData = new Metadata();
    var meta = new Meta();
    meta.setContent("cover-id");
    meta.setName("cover");
    metaData.setMeta(List.of(meta));
    opf.setMetadata(metaData);
    var manifest = new Manifest();
    var coverItem = new Item();
    var otherItem1 = new Item();
    var otherItem2 = new Item();
    coverItem.setId("cover-id");
    coverItem.setHref("cover-path");
    manifest.setItems(List.of(otherItem1, coverItem, otherItem2));
    opf.setManifest(manifest);
    var path = reader.getCoverPath(opf);
    assertThat(path).isEqualTo("cover-path");
  }
}

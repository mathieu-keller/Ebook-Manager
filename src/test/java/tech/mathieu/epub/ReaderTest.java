package tech.mathieu.epub;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import java.io.File;
import java.util.zip.ZipFile;
import javax.inject.Inject;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import tech.mathieu.PostgresResource;

@QuarkusTestResource(PostgresResource.class)
@QuarkusTest
@TestTransaction
class ReaderTest {

  @Inject Reader reader;

  @Test
  void test_parse_ebook() throws Exception {
    var testFile = new File(getClass().getResource("/epub/TestBook.epub").toURI());
    var testZipFile = new ZipFile(testFile);
    var book = reader.read(testZipFile);

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
          softly.assertThat(book.getMetadata().getCreators().get(0).getId()).isEqualTo("creator0");

          // contributors
          softly.assertThat(book.getMetadata().getContributors()).isNull();

          // publishers
          softly.assertThat(book.getMetadata().getPublishers()).hasSize(1);
          softly
              .assertThat(book.getMetadata().getPublishers().get(0).getValue())
              .isEqualTo("Test Publisher");

          // identifier
          softly.assertThat(book.getMetadata().getIdentifiers()).hasSize(1);
          softly.assertThat(book.getMetadata().getIdentifiers().get(0).getId()).isEqualTo("p1234");
          softly
              .assertThat(book.getMetadata().getIdentifiers().get(0).getValue())
              .isEqualTo("urn:isbn:0001112223334");

          // dates
          softly.assertThat(book.getMetadata().getDates()).hasSize(1);
          softly
              .assertThat(book.getMetadata().getDates().get(0).getValue())
              .isEqualTo("2020-03-04");

          // subjects
          softly.assertThat(book.getMetadata().getSubjects()).hasSize(6);
          var subjects =
              book.getMetadata().getSubjects().stream().map(subject -> subject.getValue()).toList();
          softly.assertThat(subjects).contains("Action");
          softly.assertThat(subjects).contains("Fantasy");
          softly.assertThat(subjects).contains("Manga");
          softly.assertThat(subjects).contains("Mythology");
          softly.assertThat(subjects).contains("School");
          softly.assertThat(subjects).contains("Shounen");

          // languages
          softly.assertThat(book.getMetadata().getLanguages()).hasSize(1);
          softly.assertThat(book.getMetadata().getLanguages().get(0).getValue()).isEqualTo("de");

          // meta
          softly.assertThat(book.getMetadata().getMeta()).hasSize(8);
        });
  }
}

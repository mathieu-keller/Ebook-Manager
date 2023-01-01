package tech.mathieu.book;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import java.io.File;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import tech.mathieu.subject.SubjectEntity;
import tech.mathieu.title.TitleEntity;

@QuarkusTest
@TestTransaction
@TestHTTPEndpoint(BookResource.class)
@TestSecurity(
    user = "testUser",
    roles = {"USER"})
class BookResourceTest {

  @Inject EntityManager entityManager;

  @Test
  void test_upload_ebook() throws Exception {
    var file = new File(getClass().getResource("/epub/3.0/TestBook.epub").toURI());
    given().multiPart(file).post("upload").then().statusCode(204);
    var books =
        entityManager.createQuery("select b from BookEntity b", BookEntity.class).getResultList();
    assertThat(books).hasSize(1);
    var book = books.get(0);
    SoftAssertions.assertSoftly(
        softly -> {
          softly.assertThat(book.getDate()).isEqualTo("2020-03-04");
          softly
              .assertThat(book.getBookPath())
              .isEqualTo("upload/ebooks/Test Book 01, -, Test Books/original.epub");
          softly
              .assertThat(book.getCoverPath())
              .isEqualTo("upload/ebooks/Test Book 01, -, Test Books/cover.jpeg");

          var titles = book.getTitleEntities().stream().map(TitleEntity::getTitle).toList();
          softly.assertThat(titles).containsExactly("Test Book 01", "-", "Test Books");

          softly.assertThat(book.getCreatorEntities()).hasSize(1);
          softly
              .assertThat(book.getCreatorEntities().get(0).getName())
              .isEqualTo("Last Name, First Name");

          softly.assertThat(book.getContributorEntities()).hasSize(0);

          softly.assertThat(book.getLanguageEntities()).hasSize(1);
          softly.assertThat(book.getLanguageEntities().get(0).getName()).isEqualTo("de");

          softly.assertThat(book.getPublisherEntities()).hasSize(1);
          softly
              .assertThat(book.getPublisherEntities().get(0).getName())
              .isEqualTo("Test Publisher");

          var subjects = book.getSubjectEntities().stream().map(SubjectEntity::getName).toList();
          softly
              .assertThat(subjects)
              .containsExactly("Action", "Fantasy", "Manga", "Mythology", "School", "Shounen");

          softly.assertThat(book.getIdentifierEntities()).hasSize(1);
          softly
              .assertThat(book.getIdentifierEntities().get(0).getValue())
              .isEqualTo("urn:isbn:0001112223334");

          softly.assertThat(book.getCollectionEntity().getName()).isEqualTo("Test Books");
          softly.assertThat(book.getGroupPosition()).isEqualTo(3);
        });
  }
}

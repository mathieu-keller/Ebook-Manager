package tech.mathieu.book;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import java.io.File;
import java.time.Duration;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestHTTPEndpoint(BookResource.class)
class BookResourceTest {

  @Test
  void test_upload_ebook() throws Exception {
    var file = new File(getClass().getResource("/epub/3.0/TestBook.epub").toURI());
    given()
        .multiPart(file)
        .post("upload")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body("id", is("urn:isbn:0001112223334"));
    Uni<Book> unIBook = Book.findAll().firstResult();
    var book = unIBook.await().atMost(Duration.ofSeconds(1));
    SoftAssertions.assertSoftly(
        softly -> {
          softly.assertThat(book.getId()).isEqualTo("urn:isbn:0001112223334");
          softly.assertThat(book.getTitle()).isEqualTo("Test Book 01");
        });
  }
}

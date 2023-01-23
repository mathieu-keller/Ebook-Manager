package tech.mathieu.book;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import java.io.File;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import tech.mathieu.creator.CreatorDto;
import tech.mathieu.factories.*;
import tech.mathieu.language.LanguageDto;
import tech.mathieu.publisher.PublisherDto;
import tech.mathieu.subject.SubjectDto;
import tech.mathieu.subject.SubjectEntity;
import tech.mathieu.testutil.DatabaseUtil;
import tech.mathieu.testutil.TestTransactionRunner;
import tech.mathieu.title.TitleEntity;

@QuarkusTest
@TestTransaction
@TestHTTPEndpoint(BookResource.class)
@TestSecurity(
    user = "testUser",
    roles = {"USER"})
class BookResourceTest {

  @Inject EntityManager entityManager;

  @Inject TestTransactionRunner testTransactionRunner;

  @Inject DatabaseUtil databaseUtil;

  @AfterEach
  public void cleanUp() {
    databaseUtil.cleanDb();
  }

  @Test
  void test_upload_ebook() throws Exception {
    var file = new File(getClass().getResource("/epub/3.0/TestBook.epub").toURI());
    given().multiPart(file).post("upload").then().statusCode(HttpStatus.SC_NO_CONTENT);
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

  @Test
  void test_upload_fake_ebook() throws Exception {
    var file = new File(getClass().getResource("/epub/FakeEbook.epub").toURI());
    given()
        .multiPart(file)
        .post("upload")
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST)
        .body(is("is not an epub file!"));
  }

  @Test
  void test_get_book_dto_but_entity_not_found() throws Exception {
    given().get("9999").then().statusCode(HttpStatus.SC_NOT_FOUND);
  }

  @Test
  void test_get_book_dto() throws Exception {
    var book =
        testTransactionRunner.runInNewTransaction(
            () -> {
              var entity =
                  new BookEntityBuilder()
                      .withGroupPosition(1L)
                      .withCollectionEntity(new CollectionEntityBuilder().build())
                      .withCreatorEntities(
                          List.of(
                              new CreatorEntityBuilder().withName("creator1").build(),
                              new CreatorEntityBuilder().withName("creator2").build()))
                      .withLanguageEntities(
                          List.of(
                              new LanguageEntityBuilder().withName("de_DE").build(),
                              new LanguageEntityBuilder().withName("en_US").build()))
                      .withPublisherEntities(
                          List.of(
                              new PublisherEntityBuilder().withName("publisher1").build(),
                              new PublisherEntityBuilder().withName("publisher2").build()))
                      .withSubjectEntities(
                          List.of(
                              new SubjectEntityBuilder().withName("subject1").build(),
                              new SubjectEntityBuilder().withName("subject2").build()))
                      .build();
              entity.setTitleEntities(
                  List.of(
                      new TitleEntityBuilder().withTitle("title1").withBookEntity(entity).build(),
                      new TitleEntityBuilder().withTitle("title2").withBookEntity(entity).build()));
              return entityManager.merge(entity);
            });

    var dto =
        given()
            .get(book.getId().toString())
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .as(BookDto.class);
    SoftAssertions.assertSoftly(
        softly -> {
          softly.assertThat(dto.id()).isEqualTo(book.getId());
          softly.assertThat(dto.title()).isEqualTo("title1, title2");
          softly.assertThat(dto.collectionIndex()).isEqualTo(1L);
          softly.assertThat(dto.collection().title()).isEqualTo("test-collection");
          softly.assertThat(dto.collection().id()).isNotNull();
          softly.assertThat(dto.collection().books()).isNull();
          softly.assertThat(dto.published()).isNull();
          softly.assertThat(dto.language().stream().allMatch(lang -> lang.id() != null)).isTrue();
          softly
              .assertThat(dto.language().stream().map(LanguageDto::name).toList())
              .containsExactly("de_DE", "en_US");
          softly.assertThat(dto.subjects().stream().allMatch(sub -> sub.id() != null)).isTrue();
          softly
              .assertThat(dto.subjects().stream().map(SubjectDto::name).toList())
              .containsExactly("subject1", "subject2");
          softly.assertThat(dto.publisher().stream().allMatch(pub -> pub.id() != null)).isTrue();
          softly
              .assertThat(dto.publisher().stream().map(PublisherDto::name).toList())
              .containsExactly("publisher1", "publisher2");
          softly.assertThat(dto.authors().stream().allMatch(auth -> auth.id() != null)).isTrue();
          softly
              .assertThat(dto.authors().stream().map(CreatorDto::name).toList())
              .containsExactly("creator1", "creator2");
        });
  }

  @Test
  void test_download_ebook() throws Exception {
    var book =
        testTransactionRunner.runInNewTransaction(
            () -> {
              var entity =
                  new BookEntityBuilder()
                      .withBookPath(
                          getClass().getResource("/epub/3.0/TestBook.epub").toURI().getPath())
                      .build();
              entity.setTitleEntities(
                  List.of(
                      new TitleEntityBuilder()
                          .withTitle("test-title")
                          .withBookEntity(entity)
                          .build()));
              return entityManager.merge(entity);
            });

    var responseFile =
        given()
            .get(book.getId() + "/download")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .header(
                "Content-Disposition",
                "attachment; filename*=UTF-8''test-title.epub;filename=\"test-title.epub\"")
            .extract()
            .asByteArray();
    var file = new File(getClass().getResource("/epub/3.0/TestBook.epub").toURI());
    assertThat(responseFile).hasSize((int) file.length());
  }

  @Test
  void test_download_ebook_file_not_found() throws Exception {
    var book =
        testTransactionRunner.runInNewTransaction(
            () -> {
              var entity =
                  new BookEntityBuilder().withBookPath("/epub/3.0/not_exists.epub").build();
              entity.setTitleEntities(
                  List.of(
                      new TitleEntityBuilder()
                          .withTitle("test-title")
                          .withBookEntity(entity)
                          .build()));
              return entityManager.merge(entity);
            });
    given().get(book.getId() + "download").then().statusCode(HttpStatus.SC_NOT_FOUND);
  }

  @Test
  void test_download_ebook_entity_not_found() throws Exception {
    given().get("9999/download").then().statusCode(HttpStatus.SC_NOT_FOUND);
  }

  @Test
  void test_get_cover_ebook() throws Exception {
    var book =
        testTransactionRunner.runInNewTransaction(
            () -> {
              var entity =
                  new BookEntityBuilder()
                      .withCoverPath(
                          getClass().getResource("/epub/3.0/cover.jpg").toURI().getPath())
                      .build();
              return entityManager.merge(entity);
            });

    var responseFile =
        given()
            .get(book.getId() + "/cover")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .asByteArray();
    var file = new File(getClass().getResource("/epub/3.0/cover.jpg").toURI());
    assertThat(responseFile).hasSize((int) file.length());
  }

  @Test
  void test_get_cover_file_path_is_null() throws Exception {
    var book =
        testTransactionRunner.runInNewTransaction(
            () -> {
              var entity = new BookEntityBuilder().withCoverPath(null).build();
              entity.setTitleEntities(
                  List.of(
                      new TitleEntityBuilder()
                          .withTitle("test-title")
                          .withBookEntity(entity)
                          .build()));
              return entityManager.merge(entity);
            });
    given().get(book.getId() + "/cover").then().statusCode(HttpStatus.SC_NOT_FOUND);
  }

  @Test
  void test_get_cover_ebook_file_not_found() throws Exception {
    var book =
        testTransactionRunner.runInNewTransaction(
            () -> {
              var entity =
                  new BookEntityBuilder().withCoverPath("/epub/3.0/not_exists.jpeg").build();
              entity.setTitleEntities(
                  List.of(
                      new TitleEntityBuilder()
                          .withTitle("test-title")
                          .withBookEntity(entity)
                          .build()));
              return entityManager.merge(entity);
            });
    given().get(book.getId() + "/cover").then().statusCode(HttpStatus.SC_NOT_FOUND);
  }

  @Test
  void test_get_cover_ebook_entity_not_found() throws Exception {
    given().get("9999/cover").then().statusCode(HttpStatus.SC_NOT_FOUND);
  }
}

package tech.mathieu.collection;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.io.File;
import java.util.List;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import tech.mathieu.factories.BookEntityBuilder;
import tech.mathieu.factories.CollectionEntityBuilder;
import tech.mathieu.testutil.DatabaseUtil;
import tech.mathieu.testutil.TestTransactionRunner;

@QuarkusTest
@TestTransaction
@TestHTTPEndpoint(CollectionResource.class)
@TestSecurity(
    user = "testUser",
    roles = {"USER"})
class CollectionResourceTest {

  @Inject EntityManager entityManager;

  @Inject TestTransactionRunner testTransactionRunner;

  @Inject DatabaseUtil databaseUtil;

  @AfterEach
  public void cleanUp() {
    databaseUtil.cleanDb();
  }

  @Test
  void test_get_collection() throws Exception {
    var collectionEntity =
        testTransactionRunner.runInNewTransaction(
            () -> {
              var book1 = entityManager.merge(new BookEntityBuilder().build());
              var book2 = entityManager.merge(new BookEntityBuilder().build());
              var collection =
                  new CollectionEntityBuilder()
                      .withName("collection-test")
                      .withBookEntities(List.of(book1, book2))
                      .build();
              book1.setCollectionEntity(collection);
              book2.setCollectionEntity(collection);
              return entityManager.merge(collection);
            });

    var dto =
        given()
            .get(collectionEntity.getId().toString())
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .as(CollectionDto.class);
    SoftAssertions.assertSoftly(
        softly -> {
          softly.assertThat(dto.id()).isEqualTo(collectionEntity.getId());
          softly.assertThat(dto.title()).isEqualTo("collection-test");
          softly.assertThat(dto.books()).hasSize(2);
        });
  }

  @Test
  void test_get_collection_but_collection_not_exist() throws Exception {
    given().get("99999").then().statusCode(HttpStatus.SC_NOT_FOUND);
  }

  @Test
  void test_get_collection_cover() throws Exception {
    var collectionEntity =
        testTransactionRunner.runInNewTransaction(
            () -> {
              var book1 =
                  entityManager.merge(
                      new BookEntityBuilder()
                          .withCoverPath("/dont/exists.jpeg")
                          .withGroupPosition(2L)
                          .build());
              var book2 =
                  entityManager.merge(
                      new BookEntityBuilder()
                          .withCoverPath(
                              getClass().getResource("/epub/3.0/cover.jpg").toURI().getPath())
                          .withGroupPosition(1L)
                          .build());
              var collection =
                  new CollectionEntityBuilder()
                      .withName("collection-test")
                      .withBookEntities(List.of(book1, book2))
                      .build();
              book1.setCollectionEntity(collection);
              book2.setCollectionEntity(collection);
              return entityManager.merge(collection);
            });
    var responseFile =
        given()
            .get(collectionEntity.getId() + "/cover")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .asByteArray();
    var file = new File(getClass().getResource("/epub/3.0/cover.jpg").toURI());
    assertThat(responseFile).hasSize((int) file.length());
  }

  @Test
  void test_get_collection_cover_but_cover_is_not_saved() throws Exception {
    var collectionEntity =
        testTransactionRunner.runInNewTransaction(
            () -> {
              var book1 =
                  entityManager.merge(
                      new BookEntityBuilder()
                          .withCoverPath("/dont/exists.jpeg")
                          .withGroupPosition(1L)
                          .build());
              var collection =
                  new CollectionEntityBuilder()
                      .withName("collection-test")
                      .withBookEntities(List.of(book1))
                      .build();
              book1.setCollectionEntity(collection);
              return entityManager.merge(collection);
            });
    given().get(collectionEntity.getId() + "/cover").then().statusCode(HttpStatus.SC_NOT_FOUND);
  }

  @Test
  void test_get_collection_cover_but_cover_path_is_null() throws Exception {
    var collectionEntity =
        testTransactionRunner.runInNewTransaction(
            () -> {
              var book1 =
                  entityManager.merge(
                      new BookEntityBuilder().withCoverPath(null).withGroupPosition(1L).build());
              var collection =
                  new CollectionEntityBuilder()
                      .withName("collection-test")
                      .withBookEntities(List.of(book1))
                      .build();
              book1.setCollectionEntity(collection);
              return entityManager.merge(collection);
            });
    given().get(collectionEntity.getId() + "/cover").then().statusCode(HttpStatus.SC_NOT_FOUND);
  }

  @Test
  void test_get_collection_cover_but_collection_has_no_books() throws Exception {
    var collectionEntity =
        testTransactionRunner.runInNewTransaction(
            () -> {
              var collection =
                  new CollectionEntityBuilder()
                      .withName("collection-test")
                      .withBookEntities(List.of())
                      .build();
              return entityManager.merge(collection);
            });
    given().get(collectionEntity.getId() + "/cover").then().statusCode(HttpStatus.SC_NOT_FOUND);
  }

  @Test
  void test_get_collection_cover_but_collection_not_exist() throws Exception {
    given().get("99999/cover").then().statusCode(HttpStatus.SC_NOT_FOUND);
  }
}

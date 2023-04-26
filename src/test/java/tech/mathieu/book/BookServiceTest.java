package tech.mathieu.book;

import static org.assertj.core.api.Assertions.assertThat;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

@QuarkusTest
class BookServiceTest {

  @Inject BookService bookService;

  @Test
  void test_check_if_epub_file_is_an_zip_expect_returning_true() throws Exception {
    assertThat(
            bookService.isArchive(
                Path.of(getClass().getResource("/epub/3.0/TestBook.epub").toURI())))
        .isTrue();
  }

  @Test
  void test_check_if_txt_file_is_not_an_zip_expect_returning_false() throws Exception {
    assertThat(
            bookService.isArchive(Path.of(getClass().getResource("/epub/FakeEbook.epub").toURI())))
        .isFalse();
  }
}

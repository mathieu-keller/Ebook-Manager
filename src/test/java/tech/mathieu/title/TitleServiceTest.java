package tech.mathieu.title;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.inject.Inject;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import tech.mathieu.PostgresResource;
import tech.mathieu.book.BookEntity;
import tech.mathieu.epub.opf.Metadata;
import tech.mathieu.epub.opf.Opf;
import tech.mathieu.epub.opf.metadata.Meta;
import tech.mathieu.epub.opf.metadata.Title;

@QuarkusTestResource(PostgresResource.class)
@QuarkusTest
@TestTransaction
class TitleServiceTest {

  @Inject TitleService titleService;

  @Test
  void test_get_title_with_id_and_without_metadata() {
    var opf = new Opf();
    var metaData = new Metadata();
    var mainTitle1 = new Title();
    mainTitle1.setValue("Book");
    mainTitle1.setId("T1");
    var mainTitle2 = new Title();
    mainTitle2.setValue("22");
    metaData.setTitles(List.of(mainTitle1, mainTitle2));
    opf.setMetadata(metaData);
    var titles = titleService.getTitle(opf, new HashMap<>(), new BookEntity());
    SoftAssertions.assertSoftly(
        softly -> {
          softly.assertThat(titles).hasSize(2);
          var title1 =
              titles.stream()
                  .filter(title -> Objects.equals(title.getTitle(), mainTitle1.getValue()))
                  .findFirst();
          var title2 =
              titles.stream()
                  .filter(title -> Objects.equals(title.getTitle(), mainTitle2.getValue()))
                  .findFirst();
          softly.assertThat(title1).isPresent();
          softly.assertThat(title2).isPresent();
          softly.assertThat(title1.get().getTitleType()).isEqualTo("main");
          softly.assertThat(title2.get().getTitleType()).isEqualTo("main");
        });
  }

  @Test
  void test_get_title_with_id_and_one_title_with_metadata_and_one_without_metadata() {
    var opf = new Opf();
    var metaData = new Metadata();
    var mainTitle1 = new Title();
    mainTitle1.setValue("Book");
    mainTitle1.setId("T1");
    var mainTitle2 = new Title();
    mainTitle2.setValue("22");
    metaData.setTitles(List.of(mainTitle1, mainTitle2));
    opf.setMetadata(metaData);

    var metaMap = new HashMap<String, Map<String, Meta>>();
    var metaT1 = new Meta();
    metaT1.setValue("main");
    metaMap.put("#T1", Map.of("title-type", metaT1));

    var titles = titleService.getTitle(opf, metaMap, new BookEntity());
    SoftAssertions.assertSoftly(
        softly -> {
          softly.assertThat(titles).hasSize(2);
          var title1 =
              titles.stream()
                  .filter(title -> Objects.equals(title.getTitle(), mainTitle1.getValue()))
                  .findFirst();
          var title2 =
              titles.stream()
                  .filter(title -> Objects.equals(title.getTitle(), mainTitle2.getValue()))
                  .findFirst();
          softly.assertThat(title1).isPresent();
          softly.assertThat(title2).isPresent();
          softly.assertThat(title1.get().getTitleType()).isEqualTo("main");
          softly.assertThat(title2.get().getTitleType()).isEqualTo("main");
        });
  }

  @Test
  void test_get_title_without_id() {
    var opf = new Opf();
    var metaData = new Metadata();
    var mainTitle1 = new Title();
    mainTitle1.setValue("Book");
    var mainTitle2 = new Title();
    mainTitle2.setValue("22");
    metaData.setTitles(List.of(mainTitle1, mainTitle2));
    opf.setMetadata(metaData);
    var titles = titleService.getTitle(opf, new HashMap<>(), new BookEntity());
    SoftAssertions.assertSoftly(
        softly -> {
          softly.assertThat(titles).hasSize(2);
          var title1 =
              titles.stream()
                  .filter(title -> Objects.equals(title.getTitle(), mainTitle1.getValue()))
                  .findFirst();
          var title2 =
              titles.stream()
                  .filter(title -> Objects.equals(title.getTitle(), mainTitle2.getValue()))
                  .findFirst();
          softly.assertThat(title1).isPresent();
          softly.assertThat(title2).isPresent();
          softly.assertThat(title1.get().getTitleType()).isEqualTo("main");
          softly.assertThat(title2.get().getTitleType()).isEqualTo("main");
        });
  }

  @Test
  void test_get_title_with_id_and_with_metadata() {
    var opf = new Opf();
    var metaData = new Metadata();
    var mainTitle1 = new Title();
    mainTitle1.setValue("Book");
    mainTitle1.setId("T1");
    var mainTitle2 = new Title();
    mainTitle2.setValue("22");
    mainTitle2.setId("T2");
    metaData.setTitles(List.of(mainTitle1, mainTitle2));
    opf.setMetadata(metaData);

    var metaMap = new HashMap<String, Map<String, Meta>>();
    var metaT1 = new Meta();
    metaT1.setValue("main");
    metaMap.put("#T1", Map.of("title-type", metaT1));
    var metaT2 = new Meta();
    metaT2.setValue("sub");
    metaMap.put("#T2", Map.of("title-type", metaT2));

    var titles = titleService.getTitle(opf, metaMap, new BookEntity());
    SoftAssertions.assertSoftly(
        softly -> {
          softly.assertThat(titles).hasSize(2);
          var title1 =
              titles.stream()
                  .filter(title -> Objects.equals(title.getTitle(), mainTitle1.getValue()))
                  .findFirst();
          var title2 =
              titles.stream()
                  .filter(title -> Objects.equals(title.getTitle(), mainTitle2.getValue()))
                  .findFirst();
          softly.assertThat(title1).isPresent();
          softly.assertThat(title2).isPresent();
          softly.assertThat(title1.get().getTitleType()).isEqualTo("main");
          softly.assertThat(title2.get().getTitleType()).isEqualTo("sub");
        });
  }
}

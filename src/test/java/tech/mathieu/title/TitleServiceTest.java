package tech.mathieu.title;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import tech.mathieu.epub.opf.Metadata;
import tech.mathieu.epub.opf.Opf;
import tech.mathieu.epub.opf.metadata.Meta;
import tech.mathieu.epub.opf.metadata.Title;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class TitleServiceTest {

  @Inject
  TitleService titleService;

  @Test
  public void test_get_title_with_id_and_without_metadata() {
    var opf = new Opf();
    var metaData = new Metadata();
    var mainTitle1 = new Title();
    mainTitle1.setValue("Book");
    mainTitle1.setId("T1");
    var mainTitle2 = new Title();
    mainTitle2.setValue("22");
    metaData.setTitles(List.of(mainTitle1, mainTitle2));
    opf.setMetadata(metaData);
    var title = titleService.getTitle(opf, new HashMap<>());
    assertThat(title).isEqualTo("Book, 22");
  }

  @Test
  public void test_get_title_with_id_and_one_title_with_metadata_and_one_without_metadata() {
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

    var title = titleService.getTitle(opf, metaMap);
    assertThat(title).isEqualTo("Book, 22");
  }

  @Test
  public void test_get_title_without_id() {
    var opf = new Opf();
    var metaData = new Metadata();
    var mainTitle1 = new Title();
    mainTitle1.setValue("Book");
    var mainTitle2 = new Title();
    mainTitle2.setValue("22");
    metaData.setTitles(List.of(mainTitle1, mainTitle2));
    opf.setMetadata(metaData);
    var title = titleService.getTitle(opf, new HashMap<>());
    assertThat(title).isEqualTo("Book, 22");
  }

  @Test
  public void test_get_title_with_id_and_with_metadata() {
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

    var title = titleService.getTitle(opf, metaMap);
    assertThat(title).isEqualTo("Book");
  }

}

package tech.mathieu.epub;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;
import tech.mathieu.epub.opf.Opf;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Objects;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@QuarkusTest
public class ReaderTest {

  @Inject
  Reader reader;

  @Test
  public void test() throws Exception {
    try (var in = getClass().getResourceAsStream("/epub/TestBook.epub")) {
      var book = reader.read(in);

      assertEquals(book.getVersion(), BigDecimal.valueOf(2.0));
      assertTitles(book);
      assertCreators(book);
      assertContributors(book);
      assertPublishers(book);
      assertIdentifiers(book);
      assertDates(book);
      assertSubjects(book);
      assertLanguages(book);
      assertMeta(book);
    }
  }

  private void assertMeta(Opf book) {
    assertNotNull(book.getMetadata().getMeta());
    assertEquals(book.getMetadata().getMeta().size(), 2);
    var metaCover = book.getMetadata().getMeta()
        .stream()
        .filter(meta -> Objects.equals(meta.getName(), "cover"))
        .findFirst();
    var metaPrimaryWritingMode = book.getMetadata().getMeta()
        .stream()
        .filter(meta -> Objects.equals(meta.getName(), "primary-writing-mode"))
        .findFirst();
    assertTrue(metaCover.isPresent());
    assertTrue(metaPrimaryWritingMode.isPresent());
    assertEquals(metaCover.get().getContent(), "cover");
    assertEquals(metaPrimaryWritingMode.get().getContent(), "horizontal-rl");
  }

  private void assertLanguages(Opf book) {
    assertNotNull(book.getMetadata().getLanguages());
    assertEquals(book.getMetadata().getLanguages().size(), 1);
    assertEquals(book.getMetadata().getLanguages().get(0).getValue(), "de");
  }

  private void assertSubjects(Opf book) {
    assertNotNull(book.getMetadata().getSubjects());
    assertEquals(book.getMetadata().getSubjects().size(), 6);
    var subjects = book.getMetadata().getSubjects()
        .stream()
        .map(subject -> subject.getValue())
        .toList();
    assertTrue(subjects.contains("Action"));
    assertTrue(subjects.contains("Fantasy"));
    assertTrue(subjects.contains("Manga"));
    assertTrue(subjects.contains("Mythology"));
    assertTrue(subjects.contains("School"));
    assertTrue(subjects.contains("Shounen"));
  }

  private void assertDates(Opf book) {
    assertNotNull(book.getMetadata().getDates());
    assertEquals(book.getMetadata().getDates().size(), 1);
    assertEquals(book.getMetadata().getDates().get(0).getValue(), "2015-03-09T23:00:00+00:00");
  }

  private void assertIdentifiers(Opf book) {
    assertNotNull(book.getMetadata().getIdentifiers());
    assertEquals(book.getMetadata().getIdentifiers().size(), 2);
    var uuidIdentifier = book.getMetadata().getIdentifiers()
        .stream()
        .filter(identifier -> Objects.equals(identifier.getScheme(), "uuid"))
        .findFirst();
    var mobiAsinIdentifier = book.getMetadata().getIdentifiers()
        .stream()
        .filter(identifier -> Objects.equals(identifier.getScheme(), "MOBI-ASIN"))
        .findFirst();
    assertTrue(uuidIdentifier.isPresent());
    assertTrue(mobiAsinIdentifier.isPresent());
    assertEquals(uuidIdentifier.get().getId(), "uuid_id");
    assertEquals(uuidIdentifier.get().getValue(), "TEST UUID");
    assertNull(mobiAsinIdentifier.get().getId());
    assertEquals(mobiAsinIdentifier.get().getValue(), "Test Ansin");
  }

  private void assertPublishers(Opf book) {
    assertNotNull(book.getMetadata().getPublishers());
    assertEquals(book.getMetadata().getPublishers().size(), 1);
    assertEquals(book.getMetadata().getPublishers().get(0).getValue(), "Test publisher");
  }

  private void assertContributors(Opf book) {
    assertNotNull(book.getMetadata().getContributors());
    assertEquals(book.getMetadata().getContributors().size(), 1);
    assertEquals(book.getMetadata().getContributors().get(0).getValue(), "Test contributor");
    assertEquals(book.getMetadata().getContributors().get(0).getRole(), "bkp");
  }

  private void assertCreators(Opf book) {
    assertNotNull(book.getMetadata().getCreators());
    assertEquals(book.getMetadata().getCreators().size(), 1);
    assertEquals(book.getMetadata().getCreators().get(0).getValue(), "Test Creator");
    assertEquals(book.getMetadata().getCreators().get(0).getRole(), "aut");
    assertEquals(book.getMetadata().getCreators().get(0).getFileAs(), "Test, Creator");
  }

  private void assertTitles(Opf book) {
    assertNotNull(book.getMetadata().getTitles());
    assertEquals(book.getMetadata().getTitles().size(), 1);
    assertEquals(book.getMetadata().getTitles().get(0).getValue(), "Test Book");
  }

}

package tech.mathieu.book;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.*;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;
import tech.mathieu.epub.Reader;
import tech.mathieu.epub.opf.Opf;
import tech.mathieu.epub.opf.metadata.DefaultAttributes;
import tech.mathieu.epub.opf.metadata.Id;

@ApplicationScoped
public class BookService {

  private final Reader reader;

  public BookService(Reader reader) {
    this.reader = reader;
  }

  // visible for test
  boolean isArchive(File file) throws IOException {
    try (var raf = new RandomAccessFile(file, "r")) {
      return switch (raf.readInt()) {
        case 0x504B0304, 0x504B0506, 0x504B0708 -> true;
        default -> false;
      };
    }
  }

  public Uni<Book> processInbox(File file) {
    try {
      if (!isArchive(file)) {
        throw new IllegalArgumentException("is not an epub file!");
      }
      ZipFile zipFile = new ZipFile(file);
      var opfWithPath = reader.read(zipFile);
      return saveBook(opfWithPath.right());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  Uni<Book> saveBook(Opf opf) {
    return getGetBook(opf).persistOrUpdate();
  }

  Book getGetBook(Opf opf) {
    return new Book()
        .setId(getBookId(opf))
        .setTitle(getTitle(opf))
        .setSubjects(getSubjects(opf))
        .setLanguages(getLanguages(opf))
        .setContributors(getContributors(opf))
        .setCreators(getCreators(opf))
        .setPublicationDate(getPublicationDate(opf));
  }

  List<String> getSubjects(Opf opf) {
    return Optional.ofNullable(opf.getMetadata().getSubjects())
        .map(subjects -> subjects.stream().map(Id::getValue).collect(Collectors.toList()))
        .orElse(null);
  }

  String getTitle(Opf opf) {
    return Optional.ofNullable(opf.getMetadata().getTitles())
        .filter(titles -> !titles.isEmpty())
        .map(titles -> titles.getFirst().getValue())
        .orElseThrow(() -> new IllegalArgumentException("No Title found!"));
  }

  List<String> getLanguages(Opf opf) {
    return Optional.ofNullable(opf.getMetadata().getLanguages())
        .map(languages -> languages.stream().map(Id::getValue).collect(Collectors.toList()))
        .orElse(null);
  }

  List<String> getContributors(Opf opf) {
    return Optional.ofNullable(opf.getMetadata().getContributors())
        .map(
            contributors ->
                contributors.stream().map(DefaultAttributes::getValue).collect(Collectors.toList()))
        .orElse(null);
  }

  List<String> getCreators(Opf opf) {
    return Optional.ofNullable(opf.getMetadata().getCreators())
        .map(
            creators ->
                creators.stream().map(DefaultAttributes::getValue).collect(Collectors.toList()))
        .orElse(null);
  }

  Instant getPublicationDate(Opf opf) {
    DateTimeFormatter formatter =
        new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd['T'HH:mm[:ss[.SSS]][z]]")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .parseDefaulting(ChronoField.NANO_OF_SECOND, 0)
            .parseDefaulting(ChronoField.OFFSET_SECONDS, 0)
            .toFormatter();
    return Optional.ofNullable(opf.getMetadata().getDates())
        .map(
            dates ->
                dates.stream()
                    .map(Id::getValue)
                    .findFirst()
                    .map(
                        date ->
                            ZonedDateTime.parse(date, formatter)
                                .withZoneSameInstant(ZoneOffset.UTC)
                                .toInstant())
                    .orElse(null))
        .orElse(null);
  }

  // visible for test
  String getBookId(Opf opf) {
    return opf.getMetadata().getIdentifiers().stream()
        .filter(identifier -> Objects.equals(identifier.getId(), opf.getUniqueIdentifier()))
        .map(Id::getValue)
        .reduce(
            (first, second) -> {
              throw new IllegalArgumentException("Too many main Identifiers found!");
            })
        .orElseThrow(() -> new IllegalArgumentException("No main Identifiers found!"));
  }
}

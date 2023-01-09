package tech.mathieu.book;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import tech.mathieu.collection.CollectionDto;
import tech.mathieu.collection.CollectionService;
import tech.mathieu.contributor.ContributorService;
import tech.mathieu.creator.CreatorDto;
import tech.mathieu.creator.CreatorService;
import tech.mathieu.epub.Reader;
import tech.mathieu.epub.opf.Opf;
import tech.mathieu.epub.opf.metadata.Date;
import tech.mathieu.epub.opf.metadata.Meta;
import tech.mathieu.exceptions.IllegalArgumentApplicationException;
import tech.mathieu.exceptions.NotFoundApplicationException;
import tech.mathieu.identifier.IdentifierService;
import tech.mathieu.language.LanguageDto;
import tech.mathieu.language.LanguageService;
import tech.mathieu.publisher.PublisherDto;
import tech.mathieu.publisher.PublisherService;
import tech.mathieu.subject.SubjectDto;
import tech.mathieu.subject.SubjectService;
import tech.mathieu.title.TitleEntity;
import tech.mathieu.title.TitleService;

@ApplicationScoped
@Transactional
public class BookService {

  @Inject EntityManager entityManager;

  @Inject SubjectService subjectService;

  @Inject CreatorService creatorService;

  @Inject PublisherService publisherService;

  @Inject LanguageService languageService;

  @Inject ContributorService contributorService;

  @Inject IdentifierService identifierService;

  @Inject TitleService titleService;

  @Inject CollectionService collectionService;

  @Inject Reader reader;

  public BookEntity getBookById(Long id) {
    return Optional.ofNullable(entityManager.find(BookEntity.class, id))
        .orElseThrow(
            () -> new NotFoundApplicationException("entity with id " + id + " not found!"));
  }

  public BookDto getBookDto(Long bookId) {
    return getBookDto(getBookById(bookId));
  }

  public BookDto getBookDto(BookEntity entity) {
    return new BookDto(
        entity.getId(),
        entity.getTitleEntities().stream()
            .map(TitleEntity::getTitle)
            .collect(Collectors.joining(", ")),
        null,
        Optional.ofNullable(entity.getLanguageEntities())
            .map(
                languageEntities ->
                    languageEntities.stream()
                        .map(
                            languageEntity ->
                                new LanguageDto(languageEntity.getId(), languageEntity.getName()))
                        .toList())
            .orElse(null),
        Optional.ofNullable(entity.getSubjectEntities())
            .map(
                subjectEntities ->
                    subjectEntities.stream()
                        .map(
                            subjectEntity ->
                                new SubjectDto(subjectEntity.getId(), subjectEntity.getName()))
                        .toList())
            .orElse(null),
        Optional.ofNullable(entity.getPublisherEntities())
            .map(
                publisherEntities ->
                    publisherEntities.stream()
                        .map(
                            publisherEntity ->
                                new PublisherDto(
                                    publisherEntity.getId(), publisherEntity.getName()))
                        .toList())
            .orElse(null),
        Optional.ofNullable(entity.getCreatorEntities())
            .map(
                creatorEntities ->
                    creatorEntities.stream()
                        .map(
                            creatorEntity ->
                                new CreatorDto(creatorEntity.getId(), creatorEntity.getName()))
                        .toList())
            .orElse(null),
        entity.collectionEntity != null
            ? new CollectionDto(
                entity.collectionEntity.getId(), entity.collectionEntity.getName(), null)
            : null,
        entity.groupPosition);
  }

  public void processInbox(Path inboxPath) throws IOException {
    try {
      var zipFile = new ZipFile(inboxPath.toFile());
      var opfWithPath = reader.read(zipFile);
      var opf = opfWithPath.right();
      var book = saveBook(opf);
      var destPath = new File(Paths.get(book.getBookPath()).getParent().toString());
      destPath.mkdirs();
      var dest = new File(destPath + "/original.epub");
      var result = new File(zipFile.getName()).renameTo(dest);
      if (!result) {
        throw new IOException("can't rename file " + zipFile.getName() + " to " + dest.getName());
      }
      reader.saveCover(opfWithPath, zipFile, destPath.toString());
    } catch (IOException e) {
      Files.deleteIfExists(inboxPath);
      throw e;
    }
  }

  @Transactional(Transactional.TxType.NEVER)
  public void saveBookToInbox(InputStream in, Path inboxPath) throws IOException {
    try (var fos = new FileOutputStream(inboxPath.toFile())) {
      int read;
      byte[] bytes = new byte[1024];
      while ((read = in.read(bytes)) != -1) {
        fos.write(bytes, 0, read);
      }
      fos.flush();
      if (!isArchive(inboxPath)) {
        Files.deleteIfExists(inboxPath);
        throw new IllegalArgumentApplicationException("is not an epub file!");
      }
    } catch (IOException e) {
      Files.deleteIfExists(inboxPath);
      throw e;
    }
  }

  // visible for test
  boolean isArchive(Path path) throws IOException {
    var fileSignature = 0;
    try (var raf = new RandomAccessFile(path.toFile(), "r")) {
      fileSignature = raf.readInt();
    }
    return fileSignature == 0x504B0304
        || fileSignature == 0x504B0506
        || fileSignature == 0x504B0708;
  }

  private BookEntity saveBook(Opf opf) {
    var book = new BookEntity();
    var metaData = new HashMap<String, Map<String, Meta>>();
    opf.getMetadata().getMeta().stream()
        .filter(meta -> meta.getRefines() != null)
        .filter(meta -> meta.getProperty() != null)
        .forEach(
            meta -> {
              var existingId = metaData.get(meta.getRefines());
              if (existingId == null) {
                metaData.put(meta.getRefines(), new HashMap<>());
                existingId = metaData.get(meta.getRefines());
              }
              existingId.put(meta.getProperty(), meta);
            });
    book.setTitleEntities(titleService.getTitle(opf, metaData, book));
    book.setMeta(getMeta(opf));
    book.setDate(getDates(opf));
    book.setCreatorEntities(creatorService.getCreators(opf));
    book.setIdentifierEntities(identifierService.getIdentifiers(opf, book));
    book.setContributorEntities(contributorService.getContributors(opf));
    book.setLanguageEntities(languageService.getLanguages(opf));
    book.setPublisherEntities(publisherService.getPublishers(opf));
    book.setSubjectEntities(subjectService.getSubjects(opf));
    var bookFolder =
        "upload/ebooks/"
            + book.getTitleEntities().stream()
                .map(TitleEntity::getTitle)
                .collect(Collectors.joining(", "));
    book.setBookPath(bookFolder + "/original.epub");
    book.setCoverPath(bookFolder + "/cover.jpeg");
    var collection = collectionService.getCollection(opf, bookFolder, metaData);
    book.setCollectionEntity(collection.getLeft());
    book.setGroupPosition(collection.getRight());
    return entityManager.merge(book);
  }

  private String getDates(Opf epub) {
    if (epub.getMetadata().getDates() != null) {
      return epub.getMetadata().getDates().stream()
          .map(Date::getValue)
          .collect(Collectors.joining(", "));
    }
    return null;
  }

  private String getMeta(Opf epub) {
    if (epub.getMetadata().getMeta() != null) {
      return epub.getMetadata().getMeta().stream()
          .map(Meta::toString)
          .collect(Collectors.joining(", "));
    }
    return null;
  }
}

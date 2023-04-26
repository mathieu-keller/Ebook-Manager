package tech.mathieu.testutil;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.stream.Collectors;
import tech.mathieu.book.BookEntity;
import tech.mathieu.collection.CollectionEntity;
import tech.mathieu.contributor.ContributorEntity;
import tech.mathieu.creator.CreatorEntity;
import tech.mathieu.identifier.IdentifierEntity;
import tech.mathieu.language.LanguageEntity;
import tech.mathieu.publisher.PublisherEntity;
import tech.mathieu.subject.SubjectEntity;
import tech.mathieu.title.TitleEntity;

@ApplicationScoped
public class DatabaseUtil {

  @Inject EntityManager entityManager;

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public void cleanDb() {
    cleanDb(
        CollectionEntity.class,
        ContributorEntity.class,
        CreatorEntity.class,
        LanguageEntity.class,
        PublisherEntity.class,
        SubjectEntity.class,
        IdentifierEntity.class,
        TitleEntity.class,
        BookEntity.class);
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public void cleanDb(Class<?>... classes) {
    var sql =
        Arrays.stream(classes)
            .map(clazz -> "TRUNCATE " + clazz.getAnnotation(Table.class).name() + " CASCADE;")
            .collect(Collectors.joining());
    entityManager.createNativeQuery(sql).executeUpdate();
  }
}

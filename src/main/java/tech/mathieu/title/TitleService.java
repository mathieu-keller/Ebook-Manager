package tech.mathieu.title;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tech.mathieu.book.BookEntity;
import tech.mathieu.epub.opf.Opf;
import tech.mathieu.epub.opf.metadata.Meta;

@ApplicationScoped
public class TitleService {

  public List<TitleEntity> getTitle(
      Opf opf, Map<String, Map<String, Meta>> metaData, BookEntity book) {
    var epubTitles = opf.getMetadata().getTitles();
    if (epubTitles == null) {
      return List.of();
    }
    var mainTitles = new ArrayList<TitleEntity>();
    epubTitles.forEach(
        epubTitle -> {
          var title = epubTitle.getValue().strip();
          var entity = new TitleEntity();
          entity.setBookEntity(book);
          entity.setTitle(title);
          entity.setTitleType("main");
          if (epubTitle.getId() != null) {
            var titleMeta = metaData.get("#" + epubTitle.getId());
            if (titleMeta != null) {
              var titleType = titleMeta.get("title-type");
              if (titleType != null) {
                entity.setTitleType(titleType.getValue());
              }
              var titleOrder = titleMeta.get("display-seq");
              if (titleOrder != null) {
                entity.setTitleOrder(Long.parseLong(titleOrder.getValue()));
              }
            }
          }
          mainTitles.add(entity);
        });

    return mainTitles;
  }
}

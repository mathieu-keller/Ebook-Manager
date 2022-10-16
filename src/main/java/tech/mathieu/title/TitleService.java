package tech.mathieu.title;

import tech.mathieu.epub.opf.Opf;
import tech.mathieu.epub.opf.metadata.Meta;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@ApplicationScoped
public class TitleService {

  public String getTitle(Opf opf, Map<String, Map<String, Meta>> metaData) {
    var titles = opf.getMetadata().getTitles();
    if (titles == null) {
      return null;
    }
    var mainTitles = new ArrayList<String>();
    titles.forEach(title -> {
      if (title.getId() == null) {
        mainTitles.add(title.getValue());
      } else {
        var titleMeta = metaData.get("#"+title.getId());
        if (titleMeta == null) {
          mainTitles.add(title.getValue());
        } else {
          var titleType = titleMeta.get("title-type");
          if (titleType == null || Objects.equals(titleType.getValue(), "main")) {
            mainTitles.add(title.getValue());
          }
        }
      }
    });

    return String.join(", ", mainTitles);
  }

}

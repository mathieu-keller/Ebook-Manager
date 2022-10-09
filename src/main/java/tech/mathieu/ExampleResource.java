package tech.mathieu;

import io.smallrye.common.annotation.Blocking;
import org.jboss.resteasy.reactive.MultipartForm;
import tech.mathieu.ebook.BookService;
import tech.mathieu.epub.Reader;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.io.FileInputStream;
import java.io.IOException;


@Path("/api/upload/multi")
@Transactional
public class ExampleResource {

  @Inject
  Reader reader;

  @Inject
  BookService bookService;

  @POST
  @Blocking
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public String hello(@MultipartForm MultipartBody form) throws IOException {
    form.file.forEach(file -> {
      try {
        bookService.saveBook(new FileInputStream(file));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    return "DONE";
  }
}

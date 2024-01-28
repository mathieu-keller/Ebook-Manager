package tech.mathieu.book;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import java.io.FileInputStream;
import java.io.IOException;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

@Path("/api/book")
public class BookResource {

  private final BookService bookService;

  public BookResource(BookService bookService) {
    this.bookService = bookService;
  }

  @Path("upload")
  @POST
  public Uni<Book> upload(@RestForm FileUpload file) {
    try (var in = new FileInputStream(file.uploadedFile().toFile())) {
      return bookService.processInbox(file.uploadedFile().toFile());
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }
}

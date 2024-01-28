package tech.mathieu.book;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
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
    return bookService.processInbox(file.uploadedFile().toFile());
  }
}

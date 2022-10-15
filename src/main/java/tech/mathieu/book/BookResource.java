package tech.mathieu.book;

import io.quarkus.vertx.http.Compressed;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Path("/api/book")
public class BookResource {

  @Inject
  BookService bookService;

  @Path("{book-title}")
  @GET
  @Compressed
  @Produces(MediaType.APPLICATION_JSON)
  public BookDto getBook(@PathParam("book-title") String bookTitle) {
    return bookService.getBookDto(bookTitle);
  }

  @Path("download/{id}")
  @GET
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public Response downloadBook(@PathParam("id") Long id) throws UnsupportedEncodingException {
    var book = bookService.getBookById(id);
    var response = Response.ok(new File(book.getPath() + "/orginal.epub"));
    var title = URLEncoder.encode(book.getTitle() + ".epub", StandardCharsets.UTF_8)
        .replace("+", "%20");
    response.header("Content-Disposition", "attachment; filename*=UTF-8''" + title);
    return response.build();
  }

}

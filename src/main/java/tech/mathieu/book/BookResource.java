package tech.mathieu.book;

import io.quarkus.vertx.http.Compressed;
import tech.mathieu.title.TitleEntity;

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
import java.util.Objects;
import java.util.stream.Collectors;

@Path("/api/book")
public class BookResource {

  @Inject
  BookService bookService;

  @Path("{id}")
  @GET
  @Compressed
  @Produces(MediaType.APPLICATION_JSON)
  public BookDto getBook(@PathParam("id") Long bookId) {
    return bookService.getBookDto(bookId);
  }

  @Path("download/{id}")
  @GET
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public Response downloadBook(@PathParam("id") Long id) throws UnsupportedEncodingException {
    var book = bookService.getBookById(id);
    var response = Response.ok(new File(book.getPath() + "/orginal.epub"));
    var title = URLEncoder.encode(
            book.getTitleEntities()
                .stream()
                .filter(titleEntity -> Objects.equals(titleEntity.getTitleType(), "main"))
                .map(TitleEntity::getTitle)
                .collect(Collectors.joining(", ")) + ".epub",
            StandardCharsets.UTF_8)
        .replace("+", "%20");
    response.header("Content-Disposition", "attachment; filename*=UTF-8''" + title);
    return response.build();
  }

}

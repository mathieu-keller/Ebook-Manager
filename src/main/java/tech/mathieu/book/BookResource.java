package tech.mathieu.book;

import io.quarkus.security.Authenticated;
import org.jboss.resteasy.reactive.MultipartForm;
import tech.mathieu.title.TitleEntity;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/api/book")
@Authenticated
public class BookResource {

  @Inject
  BookService bookService;

  @Path("{id}")
  @GET
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

  @Path("upload")
  @POST
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public void upload(@MultipartForm BookUpload input) {
    var uuid = String.valueOf(UUID.randomUUID());
    var inboxPath = "upload/inbox";
    new File(inboxPath).mkdirs();
    var inboxBookPath = inboxPath + "/" + uuid + ".epub";
    try (var in = input.getFile()) {
      bookService.saveBookToInbox(in, inboxBookPath);
      bookService.processInbox(inboxBookPath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @GET
  @Path("{id}/cover")
  public File getCover(@PathParam("id") Long id) {
    var coverPath = bookService.getBookById(id).getPath() + "/cover.jpeg";
    var cover = new File(coverPath);
    if (cover.exists()) {
      return cover;
    }
    return null;
  }

}

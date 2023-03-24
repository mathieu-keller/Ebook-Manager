package tech.mathieu.book;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import tech.mathieu.exceptions.NotFoundApplicationException;
import tech.mathieu.title.TitleEntity;

@Path("/api/book")
@DenyAll
public class BookResource {

  @Inject BookService bookService;

  @Path("{id}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed("USER")
  public BookDto getBook(@PathParam("id") Long bookId) {
    return bookService.getBookDto(bookId);
  }

  @Path("{id}/download")
  @GET
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @RolesAllowed("USER")
  public Response downloadBook(@PathParam("id") Long id) {
    var book = bookService.getBookById(id);
    var file = new File(book.getBookPath());
    if (!file.exists()) {
      throw new NotFoundApplicationException("file not found!");
    }
    var title =
        URLEncoder.encode(
                book.getTitleEntities().stream()
                        .filter(titleEntity -> Objects.equals(titleEntity.getTitleType(), "main"))
                        .map(TitleEntity::getTitle)
                        .collect(Collectors.joining(", "))
                    + ".epub",
                StandardCharsets.UTF_8)
            .replace("+", "%20");
    return Response.ok(file)
        .header(
            "Content-Disposition",
            "attachment; filename*=UTF-8''" + title + ";filename=\"" + title + "\"")
        .build();
  }

  @Path("upload")
  @POST
  @RolesAllowed("USER")
  public void upload(@RestForm FileUpload file) {
    var uuid = String.valueOf(UUID.randomUUID());
    var inboxPath = Paths.get("upload", "inbox");
    new File(inboxPath.toUri()).mkdirs();
    var inboxBookPath = Paths.get(inboxPath.toString(), uuid + ".epub");
    try (var in = new FileInputStream(file.uploadedFile().toFile())) {
      bookService.saveBookToInbox(in, inboxBookPath);
      bookService.processInbox(inboxBookPath);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  @GET
  @Path("{id}/cover")
  @RolesAllowed("USER")
  public File getCover(@PathParam("id") Long id) {
    var entity = bookService.getBookById(id);
    if (entity.getCoverPath() == null) {
      throw new NotFoundApplicationException("cover path is null!");
    }
    var coverPath = entity.getCoverPath();
    var cover = new File(coverPath);
    if (cover.exists()) {
      return cover;
    }
    throw new NotFoundApplicationException("cover not found!");
  }
}

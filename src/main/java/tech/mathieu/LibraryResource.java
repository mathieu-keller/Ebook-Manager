package tech.mathieu;

import io.smallrye.common.annotation.Blocking;
import org.jboss.resteasy.reactive.MultipartForm;
import tech.mathieu.ebook.BookService;
import tech.mathieu.ebook.LibraryDto;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Path("/api/library")
public class LibraryResource {

  @Inject
  BookService bookService;

  @GET
  @Path(("/all"))
  @Produces(MediaType.APPLICATION_JSON)
  public List<LibraryDto> getLibraryItems() {
   return bookService.getDtos();
  }
}

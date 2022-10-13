package tech.mathieu;

import tech.mathieu.ebook.BookService;
import tech.mathieu.ebook.LibraryDto;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List ;

@Path("/api/library")
public class LibraryResource {

  @Inject
  BookService bookService;

  @GET
  @Path(("/all"))
  @Produces(MediaType.APPLICATION_JSON)
  public List<LibraryDto> getLibraryItems(@QueryParam("page") Integer page) {
   return bookService.getDtos(page);
  }
}

package tech.mathieu.library;

import io.quarkus.vertx.http.Compressed;

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
  LibraryService libraryService;

  @GET
  @Path(("/all"))
  @Compressed
  @Produces(MediaType.APPLICATION_JSON)
  public List<LibraryDto> getLibraryItems(@QueryParam("page") Integer page) {
   return libraryService.getDtos(page);
  }
}

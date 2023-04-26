package tech.mathieu.library;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/library")
@DenyAll
public class LibraryResource {

  @Inject LibraryService libraryService;

  @GET
  @Path("/")
  @RolesAllowed("USER")
  @Produces(MediaType.APPLICATION_JSON)
  public List<LibraryDto> getLibraryItems(
      @QueryParam("page") Integer page, @QueryParam("q") String search) {
    return libraryService.getDtos(page, search);
  }
}

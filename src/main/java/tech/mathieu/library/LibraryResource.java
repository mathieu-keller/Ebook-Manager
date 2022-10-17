package tech.mathieu.library;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/library")
@DenyAll
public class LibraryResource {

  @Inject
  LibraryService libraryService;

  @GET
  @Path(("/"))
  @RolesAllowed("USER")
  @Produces(MediaType.APPLICATION_JSON)
  public List<LibraryDto> getLibraryItems(@QueryParam("page") Integer page, @QueryParam("q") String search) {
    return libraryService.getDtos(page, search);
  }
}

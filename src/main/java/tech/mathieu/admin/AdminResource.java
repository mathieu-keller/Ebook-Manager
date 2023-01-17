package tech.mathieu.admin;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/api/admin")
@DenyAll
public class AdminResource {

  @Path("{id}")
  @GET
  @RolesAllowed("ADMIN")
  public String getBook(@PathParam("id") Long id) {
    return id.toString();
  }
}

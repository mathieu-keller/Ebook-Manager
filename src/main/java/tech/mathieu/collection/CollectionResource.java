package tech.mathieu.collection;

import java.io.File;
import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/api/collection")
@DenyAll
public class CollectionResource {

  @Inject CollectionService collectionService;

  @GET
  @Path("{id}")
  @RolesAllowed("USER")
  public CollectionDto getCollectionItems(@PathParam("id") Long id) {
    return collectionService.getDtos(id);
  }

  @GET
  @Path("{id}/cover")
  @RolesAllowed("USER")
  public File getCover(@PathParam("id") Long id) {
    var coverPath = collectionService.getFirstBookCoverPath(id);
    var cover = new File(coverPath);
    if (cover.exists()) {
      return cover;
    }
    return null;
  }
}

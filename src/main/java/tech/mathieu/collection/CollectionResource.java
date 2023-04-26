package tech.mathieu.collection;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.io.File;
import tech.mathieu.exceptions.NotFoundApplicationException;

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
    if (coverPath == null) {
      throw new NotFoundApplicationException("cover for collection(ID: " + id + ") not found");
    }
    var cover = new File(coverPath);
    if (!cover.exists()) {
      throw new NotFoundApplicationException("cover for collection(ID: " + id + ") not found");
    }
    return cover;
  }
}

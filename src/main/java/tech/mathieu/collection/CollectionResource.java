package tech.mathieu.collection;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.io.File;

@Path("/api/collection")
public class CollectionResource {

  @Inject
  CollectionService collectionService;


  @GET
  @Path("{id}")
  public CollectionDto getCollectionItems(@PathParam("id") Long id) {
    return collectionService.getDtos(id);
  }

  @GET
  @Path("{id}/cover")
  public File getCover(@PathParam("id") Long id) {
    var coverPath = collectionService.getCollectionCover(id) + "/cover.jpeg";
    return new File(coverPath);
  }
}

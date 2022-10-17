package tech.mathieu.collection;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/api/collection")
public class CollectionResource {

  @Inject
  CollectionService collectionService;


  @GET
  @Path("{id}")
  public CollectionDto getCollectionItems(@PathParam("id") Long id) {
    return collectionService.getDtos(id);
  }
}

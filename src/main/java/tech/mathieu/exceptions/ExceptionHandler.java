package tech.mathieu.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.Objects;
import org.jboss.logging.Logger;

@Provider
public class ExceptionHandler implements ExceptionMapper<ApplicationException> {

  private static final Logger LOGGER = Logger.getLogger(ExceptionHandler.class);

  @Override
  public Response toResponse(ApplicationException e) {
    LOGGER.error(e.getMessage(), e);
    if (Objects.equals(e.getClass(), IllegalArgumentApplicationException.class)) {
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
    if (Objects.equals(e.getClass(), NotFoundApplicationException.class)) {
      return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
  }
}

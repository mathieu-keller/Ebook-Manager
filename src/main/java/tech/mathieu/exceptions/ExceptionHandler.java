package tech.mathieu.exceptions;

import java.util.Objects;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class ExceptionHandler implements ExceptionMapper<RuntimeException> {

  private static final Logger LOGGER = Logger.getLogger(ExceptionHandler.class);

  @Override
  public Response toResponse(RuntimeException e) {
    LOGGER.error(e.getMessage(), e);
    if (Objects.equals(e.getClass(), IllegalArgumentException.class)) {
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
  }
}

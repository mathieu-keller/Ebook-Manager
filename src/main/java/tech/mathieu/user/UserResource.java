package tech.mathieu.user;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/api/user")
@DenyAll
public class UserResource {
  @Inject UserService userService;

  @POST
  @Path("/register")
  @RolesAllowed("ADMIN")
  public void register(UserDto userDto) {
    userService.createUser(userDto.username(), userDto.password());
  }

  public record UserDto(String username, String password) {}
}

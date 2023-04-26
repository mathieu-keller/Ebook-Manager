package tech.mathieu.user;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

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

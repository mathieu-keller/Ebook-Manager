package tech.mathieu.user;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Transactional
public class UserService {

  @Inject EntityManager entityManager;

  @ConfigProperty(name = "login.salt")
  Optional<String> salt;

  @ConfigProperty(name = "login.iteration", defaultValue = "16")
  int iteration;

  public void createUser(String username, String password) {
    createUser(username, password, "USER");
  }

  public void createUser(String username, String password, String roles) {
    isSaltConfigured();
    var user = new UserEntity();
    user.setName(username);
    var hashPassword =
        BcryptUtil.bcryptHash(password, iteration, salt.get().getBytes(StandardCharsets.UTF_8));
    user.setPassword(hashPassword);
    user.setRoles(roles);
    entityManager.persist(user);
  }

  private void isSaltConfigured() {
    if (salt.isEmpty()) {
      throw new IllegalStateException("no salt is configured!");
    }
    if (salt.get().length() != 16) {
      throw new IllegalStateException("salt must have a length of 16");
    }
  }
}

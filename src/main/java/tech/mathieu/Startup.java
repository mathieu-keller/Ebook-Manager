package tech.mathieu;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import tech.mathieu.user.UserEntity;
import tech.mathieu.user.UserService;

@Singleton
public class Startup {

  private static final Logger LOGGER = Logger.getLogger(Startup.class);

  @Inject UserService userService;

  @Inject EntityManager entityManager;

  @ConfigProperty(name = "login.admin.username")
  Optional<String> adminUsername;

  @ConfigProperty(name = "login.admin.password")
  Optional<String> adminPassword;

  @Transactional
  public void createAdminUser(@Observes StartupEvent evt) {
    if (adminUsername.isPresent() && adminPassword.isPresent()) {
      var user =
          entityManager
              .createQuery("select u from UserEntity u where u.name = :name ", UserEntity.class)
              .setParameter("name", adminUsername.get())
              .setMaxResults(1)
              .getResultList()
              .stream()
              .findFirst();
      if (user.isEmpty()) {
        userService.createUser(adminUsername.get(), adminPassword.get(), "USER,ADMIN");
        LOGGER.info("User " + adminUsername.get() + " created!");
      } else {
        LOGGER.info("User " + adminUsername.get() + " exists already!");
      }
    }
  }
}

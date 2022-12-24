package tech.mathieu;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import java.util.Collections;
import java.util.Map;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresResource implements QuarkusTestResourceLifecycleManager {

  static PostgreSQLContainer<?> db =
      new PostgreSQLContainer<>("postgres:latest")
          .withDatabaseName("test")
          .withUsername("test")
          .withPassword("test");

  @Override
  public Map<String, String> start() {
    db.start();
    return Collections.singletonMap("quarkus.datasource.url", db.getJdbcUrl());
  }

  @Override
  public void stop() {
    db.stop();
  }
}

package tech.mathieu.user;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import javax.persistence.*;

@Entity
@Table(name = "EBOOK_USER")
@UserDefinition
public class UserEntity {
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Id
  @Column(name = "id", nullable = false)
  Long id;

  @Column(name = "NAME", nullable = false, length = 4000)
  @Username
  String name;

  @Column(name = "PASSWORD", nullable = false, length = 4000)
  @Password
  String password;

  @Roles
  @Column(name = "ROLES", nullable = false, length = 4000)
  String roles;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRoles() {
    return roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }

  @Override
  public String toString() {
    return "UserEntity{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}

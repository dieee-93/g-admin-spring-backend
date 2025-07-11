package diego.soro.graphql.generated.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.graphql.generated.Generated
public class UserGQL {
  private String id;

  private String firstName;

  private String lastName;

  private String email;

  private String username;

  private String keycloakId;

  private List<RoleGQL> roles;

  private List<BranchGQL> branches;

  private boolean active;

  private String createdAt;

  private String updatedAt;

  public UserGQL() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getKeycloakId() {
    return keycloakId;
  }

  public void setKeycloakId(String keycloakId) {
    this.keycloakId = keycloakId;
  }

  public List<RoleGQL> getRoles() {
    return roles;
  }

  public void setRoles(List<RoleGQL> roles) {
    this.roles = roles;
  }

  public List<BranchGQL> getBranches() {
    return branches;
  }

  public void setBranches(List<BranchGQL> branches) {
    this.branches = branches;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public String toString() {
    return "UserGQL{id='" + id + "', firstName='" + firstName + "', lastName='" + lastName + "', email='" + email + "', username='" + username + "', keycloakId='" + keycloakId + "', roles='" + roles + "', branches='" + branches + "', active='" + active + "', createdAt='" + createdAt + "', updatedAt='" + updatedAt + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserGQL that = (UserGQL) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(firstName, that.firstName) &&
        Objects.equals(lastName, that.lastName) &&
        Objects.equals(email, that.email) &&
        Objects.equals(username, that.username) &&
        Objects.equals(keycloakId, that.keycloakId) &&
        Objects.equals(roles, that.roles) &&
        Objects.equals(branches, that.branches) &&
        active == that.active &&
        Objects.equals(createdAt, that.createdAt) &&
        Objects.equals(updatedAt, that.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, email, username, keycloakId, roles, branches, active, createdAt, updatedAt);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.graphql.generated.Generated
  public static class Builder {
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String keycloakId;

    private List<RoleGQL> roles;

    private List<BranchGQL> branches;

    private boolean active;

    private String createdAt;

    private String updatedAt;

    public UserGQL build() {
      UserGQL result = new UserGQL();
      result.id = this.id;
      result.firstName = this.firstName;
      result.lastName = this.lastName;
      result.email = this.email;
      result.username = this.username;
      result.keycloakId = this.keycloakId;
      result.roles = this.roles;
      result.branches = this.branches;
      result.active = this.active;
      result.createdAt = this.createdAt;
      result.updatedAt = this.updatedAt;
      return result;
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder username(String username) {
      this.username = username;
      return this;
    }

    public Builder keycloakId(String keycloakId) {
      this.keycloakId = keycloakId;
      return this;
    }

    public Builder roles(List<RoleGQL> roles) {
      this.roles = roles;
      return this;
    }

    public Builder branches(List<BranchGQL> branches) {
      this.branches = branches;
      return this;
    }

    public Builder active(boolean active) {
      this.active = active;
      return this;
    }

    public Builder createdAt(String createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Builder updatedAt(String updatedAt) {
      this.updatedAt = updatedAt;
      return this;
    }
  }
}

package diego.soro.graphql.generated.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.graphql.generated.Generated
public class RoleGQL {
  private String id;

  private String name;

  private String description;

  private boolean isSystem;

  private List<PermissionGQL> permissions;

  private RoleGQL parentRole;

  private String createdAt;

  private String updatedAt;

  private boolean active;

  public RoleGQL() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isIsSystem() {
    return isSystem;
  }

  public void setIsSystem(boolean isSystem) {
    this.isSystem = isSystem;
  }

  public List<PermissionGQL> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<PermissionGQL> permissions) {
    this.permissions = permissions;
  }

  public RoleGQL getParentRole() {
    return parentRole;
  }

  public void setParentRole(RoleGQL parentRole) {
    this.parentRole = parentRole;
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

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public String toString() {
    return "RoleGQL{id='" + id + "', name='" + name + "', description='" + description + "', isSystem='" + isSystem + "', permissions='" + permissions + "', parentRole='" + parentRole + "', createdAt='" + createdAt + "', updatedAt='" + updatedAt + "', active='" + active + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RoleGQL that = (RoleGQL) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(name, that.name) &&
        Objects.equals(description, that.description) &&
        isSystem == that.isSystem &&
        Objects.equals(permissions, that.permissions) &&
        Objects.equals(parentRole, that.parentRole) &&
        Objects.equals(createdAt, that.createdAt) &&
        Objects.equals(updatedAt, that.updatedAt) &&
        active == that.active;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, isSystem, permissions, parentRole, createdAt, updatedAt, active);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.graphql.generated.Generated
  public static class Builder {
    private String id;

    private String name;

    private String description;

    private boolean isSystem;

    private List<PermissionGQL> permissions;

    private RoleGQL parentRole;

    private String createdAt;

    private String updatedAt;

    private boolean active;

    public RoleGQL build() {
      RoleGQL result = new RoleGQL();
      result.id = this.id;
      result.name = this.name;
      result.description = this.description;
      result.isSystem = this.isSystem;
      result.permissions = this.permissions;
      result.parentRole = this.parentRole;
      result.createdAt = this.createdAt;
      result.updatedAt = this.updatedAt;
      result.active = this.active;
      return result;
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder isSystem(boolean isSystem) {
      this.isSystem = isSystem;
      return this;
    }

    public Builder permissions(List<PermissionGQL> permissions) {
      this.permissions = permissions;
      return this;
    }

    public Builder parentRole(RoleGQL parentRole) {
      this.parentRole = parentRole;
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

    public Builder active(boolean active) {
      this.active = active;
      return this;
    }
  }
}

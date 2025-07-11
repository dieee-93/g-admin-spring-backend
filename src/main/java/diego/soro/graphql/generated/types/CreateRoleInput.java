package diego.soro.graphql.generated.types;

import jakarta.annotation.Generated;
import java.lang.Boolean;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.graphql.generated.Generated
public class CreateRoleInput {
  private String name;

  private String description;

  private Boolean isSystem;

  private String parentRoleId;

  private List<String> permissionIds;

  public CreateRoleInput() {
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

  public Boolean getIsSystem() {
    return isSystem;
  }

  public void setIsSystem(Boolean isSystem) {
    this.isSystem = isSystem;
  }

  public String getParentRoleId() {
    return parentRoleId;
  }

  public void setParentRoleId(String parentRoleId) {
    this.parentRoleId = parentRoleId;
  }

  public List<String> getPermissionIds() {
    return permissionIds;
  }

  public void setPermissionIds(List<String> permissionIds) {
    this.permissionIds = permissionIds;
  }

  @Override
  public String toString() {
    return "CreateRoleInput{name='" + name + "', description='" + description + "', isSystem='" + isSystem + "', parentRoleId='" + parentRoleId + "', permissionIds='" + permissionIds + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CreateRoleInput that = (CreateRoleInput) o;
    return Objects.equals(name, that.name) &&
        Objects.equals(description, that.description) &&
        Objects.equals(isSystem, that.isSystem) &&
        Objects.equals(parentRoleId, that.parentRoleId) &&
        Objects.equals(permissionIds, that.permissionIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, isSystem, parentRoleId, permissionIds);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.graphql.generated.Generated
  public static class Builder {
    private String name;

    private String description;

    private Boolean isSystem;

    private String parentRoleId;

    private List<String> permissionIds;

    public CreateRoleInput build() {
      CreateRoleInput result = new CreateRoleInput();
      result.name = this.name;
      result.description = this.description;
      result.isSystem = this.isSystem;
      result.parentRoleId = this.parentRoleId;
      result.permissionIds = this.permissionIds;
      return result;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder isSystem(Boolean isSystem) {
      this.isSystem = isSystem;
      return this;
    }

    public Builder parentRoleId(String parentRoleId) {
      this.parentRoleId = parentRoleId;
      return this;
    }

    public Builder permissionIds(List<String> permissionIds) {
      this.permissionIds = permissionIds;
      return this;
    }
  }
}

package diego.soro.graphql.generated.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.graphql.generated.Generated
public class PermissionGQL {
  private String id;

  private String code;

  private String name;

  private String description;

  private boolean isSystem;

  private String createdAt;

  private String updatedAt;

  private boolean active;

  public PermissionGQL() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
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
    return "PermissionGQL{id='" + id + "', code='" + code + "', name='" + name + "', description='" + description + "', isSystem='" + isSystem + "', createdAt='" + createdAt + "', updatedAt='" + updatedAt + "', active='" + active + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PermissionGQL that = (PermissionGQL) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(code, that.code) &&
        Objects.equals(name, that.name) &&
        Objects.equals(description, that.description) &&
        isSystem == that.isSystem &&
        Objects.equals(createdAt, that.createdAt) &&
        Objects.equals(updatedAt, that.updatedAt) &&
        active == that.active;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code, name, description, isSystem, createdAt, updatedAt, active);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.graphql.generated.Generated
  public static class Builder {
    private String id;

    private String code;

    private String name;

    private String description;

    private boolean isSystem;

    private String createdAt;

    private String updatedAt;

    private boolean active;

    public PermissionGQL build() {
      PermissionGQL result = new PermissionGQL();
      result.id = this.id;
      result.code = this.code;
      result.name = this.name;
      result.description = this.description;
      result.isSystem = this.isSystem;
      result.createdAt = this.createdAt;
      result.updatedAt = this.updatedAt;
      result.active = this.active;
      return result;
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder code(String code) {
      this.code = code;
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

package diego.soro.graphql.generated.types;

import jakarta.annotation.Generated;
import java.lang.Boolean;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.graphql.generated.Generated
public class CreatePermissionInput {
  private String code;

  private String name;

  private String description;

  private Boolean isSystem;

  public CreatePermissionInput() {
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

  public Boolean getIsSystem() {
    return isSystem;
  }

  public void setIsSystem(Boolean isSystem) {
    this.isSystem = isSystem;
  }

  @Override
  public String toString() {
    return "CreatePermissionInput{code='" + code + "', name='" + name + "', description='" + description + "', isSystem='" + isSystem + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CreatePermissionInput that = (CreatePermissionInput) o;
    return Objects.equals(code, that.code) &&
        Objects.equals(name, that.name) &&
        Objects.equals(description, that.description) &&
        Objects.equals(isSystem, that.isSystem);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, name, description, isSystem);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.graphql.generated.Generated
  public static class Builder {
    private String code;

    private String name;

    private String description;

    private Boolean isSystem;

    public CreatePermissionInput build() {
      CreatePermissionInput result = new CreatePermissionInput();
      result.code = this.code;
      result.name = this.name;
      result.description = this.description;
      result.isSystem = this.isSystem;
      return result;
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

    public Builder isSystem(Boolean isSystem) {
      this.isSystem = isSystem;
      return this;
    }
  }
}

package diego.soro.g-admin.generated.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.g-admin.generated.Generated
public class RawMaterialCategoryInput {
  private int fatherCategoryId;

  private String name;

  public RawMaterialCategoryInput() {
  }

  public int getFatherCategoryId() {
    return fatherCategoryId;
  }

  public void setFatherCategoryId(int fatherCategoryId) {
    this.fatherCategoryId = fatherCategoryId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "RawMaterialCategoryInput{fatherCategoryId='" + fatherCategoryId + "', name='" + name + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RawMaterialCategoryInput that = (RawMaterialCategoryInput) o;
    return fatherCategoryId == that.fatherCategoryId &&
        Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fatherCategoryId, name);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.g-admin.generated.Generated
  public static class Builder {
    private int fatherCategoryId;

    private String name;

    public RawMaterialCategoryInput build() {
      RawMaterialCategoryInput result = new RawMaterialCategoryInput();
      result.fatherCategoryId = this.fatherCategoryId;
      result.name = this.name;
      return result;
    }

    public Builder fatherCategoryId(int fatherCategoryId) {
      this.fatherCategoryId = fatherCategoryId;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }
  }
}

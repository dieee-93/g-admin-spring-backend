package diego.soro.graphql.generated.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.graphql.generated.Generated
public class RAW_MATERIAL_GQLInput {
  private String name;

  private String category;

  private RAW_MATERIAL_TYPE rawMaterialType;

  public RAW_MATERIAL_GQLInput() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public RAW_MATERIAL_TYPE getRawMaterialType() {
    return rawMaterialType;
  }

  public void setRawMaterialType(RAW_MATERIAL_TYPE rawMaterialType) {
    this.rawMaterialType = rawMaterialType;
  }

  @Override
  public String toString() {
    return "RAW_MATERIAL_GQLInput{name='" + name + "', category='" + category + "', rawMaterialType='" + rawMaterialType + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RAW_MATERIAL_GQLInput that = (RAW_MATERIAL_GQLInput) o;
    return Objects.equals(name, that.name) &&
        Objects.equals(category, that.category) &&
        Objects.equals(rawMaterialType, that.rawMaterialType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, category, rawMaterialType);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.graphql.generated.Generated
  public static class Builder {
    private String name;

    private String category;

    private RAW_MATERIAL_TYPE rawMaterialType;

    public RAW_MATERIAL_GQLInput build() {
      RAW_MATERIAL_GQLInput result = new RAW_MATERIAL_GQLInput();
      result.name = this.name;
      result.category = this.category;
      result.rawMaterialType = this.rawMaterialType;
      return result;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder category(String category) {
      this.category = category;
      return this;
    }

    public Builder rawMaterialType(RAW_MATERIAL_TYPE rawMaterialType) {
      this.rawMaterialType = rawMaterialType;
      return this;
    }
  }
}

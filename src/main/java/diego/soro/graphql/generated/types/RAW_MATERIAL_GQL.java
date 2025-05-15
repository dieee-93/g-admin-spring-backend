package diego.soro.graphql.generated.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.graphql.generated.Generated
public class RAW_MATERIAL_GQL {
  private String id;

  private String name;

  private CATEGORY_GQL category;

  private String rawMaterialType;

  public RAW_MATERIAL_GQL() {
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

  public CATEGORY_GQL getCategory() {
    return category;
  }

  public void setCategory(CATEGORY_GQL category) {
    this.category = category;
  }

  public String getRawMaterialType() {
    return rawMaterialType;
  }

  public void setRawMaterialType(String rawMaterialType) {
    this.rawMaterialType = rawMaterialType;
  }

  @Override
  public String toString() {
    return "RAW_MATERIAL_GQL{id='" + id + "', name='" + name + "', category='" + category + "', rawMaterialType='" + rawMaterialType + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RAW_MATERIAL_GQL that = (RAW_MATERIAL_GQL) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(name, that.name) &&
        Objects.equals(category, that.category) &&
        Objects.equals(rawMaterialType, that.rawMaterialType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, category, rawMaterialType);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.graphql.generated.Generated
  public static class Builder {
    private String id;

    private String name;

    private CATEGORY_GQL category;

    private String rawMaterialType;

    public RAW_MATERIAL_GQL build() {
      RAW_MATERIAL_GQL result = new RAW_MATERIAL_GQL();
      result.id = this.id;
      result.name = this.name;
      result.category = this.category;
      result.rawMaterialType = this.rawMaterialType;
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

    public Builder category(CATEGORY_GQL category) {
      this.category = category;
      return this;
    }

    public Builder rawMaterialType(String rawMaterialType) {
      this.rawMaterialType = rawMaterialType;
      return this;
    }
  }
}

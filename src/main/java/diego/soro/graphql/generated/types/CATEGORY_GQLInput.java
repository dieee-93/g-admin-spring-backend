package diego.soro.graphql.generated.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.graphql.generated.Generated
public class CATEGORY_GQLInput {
  private String name;

  private String parentId;

  private List<String> childrenIds;

  private List<String> productsIds;

  private List<String> rawMaterialsIds;

  public CATEGORY_GQLInput() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public List<String> getChildrenIds() {
    return childrenIds;
  }

  public void setChildrenIds(List<String> childrenIds) {
    this.childrenIds = childrenIds;
  }

  public List<String> getProductsIds() {
    return productsIds;
  }

  public void setProductsIds(List<String> productsIds) {
    this.productsIds = productsIds;
  }

  public List<String> getRawMaterialsIds() {
    return rawMaterialsIds;
  }

  public void setRawMaterialsIds(List<String> rawMaterialsIds) {
    this.rawMaterialsIds = rawMaterialsIds;
  }

  @Override
  public String toString() {
    return "CATEGORY_GQLInput{name='" + name + "', parentId='" + parentId + "', childrenIds='" + childrenIds + "', productsIds='" + productsIds + "', rawMaterialsIds='" + rawMaterialsIds + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CATEGORY_GQLInput that = (CATEGORY_GQLInput) o;
    return Objects.equals(name, that.name) &&
        Objects.equals(parentId, that.parentId) &&
        Objects.equals(childrenIds, that.childrenIds) &&
        Objects.equals(productsIds, that.productsIds) &&
        Objects.equals(rawMaterialsIds, that.rawMaterialsIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, parentId, childrenIds, productsIds, rawMaterialsIds);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.graphql.generated.Generated
  public static class Builder {
    private String name;

    private String parentId;

    private List<String> childrenIds;

    private List<String> productsIds;

    private List<String> rawMaterialsIds;

    public CATEGORY_GQLInput build() {
      CATEGORY_GQLInput result = new CATEGORY_GQLInput();
      result.name = this.name;
      result.parentId = this.parentId;
      result.childrenIds = this.childrenIds;
      result.productsIds = this.productsIds;
      result.rawMaterialsIds = this.rawMaterialsIds;
      return result;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder parentId(String parentId) {
      this.parentId = parentId;
      return this;
    }

    public Builder childrenIds(List<String> childrenIds) {
      this.childrenIds = childrenIds;
      return this;
    }

    public Builder productsIds(List<String> productsIds) {
      this.productsIds = productsIds;
      return this;
    }

    public Builder rawMaterialsIds(List<String> rawMaterialsIds) {
      this.rawMaterialsIds = rawMaterialsIds;
      return this;
    }
  }
}

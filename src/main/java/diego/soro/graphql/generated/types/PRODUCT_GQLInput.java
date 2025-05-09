package diego.soro.graphql.generated.types;

import jakarta.annotation.Generated;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.graphql.generated.Generated
public class PRODUCT_GQLInput {
  private String name;

  private String description;

  private int categoryId;

  private double productionCost;

  private List<Integer> recipeIds;

  public PRODUCT_GQLInput() {
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

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public double getProductionCost() {
    return productionCost;
  }

  public void setProductionCost(double productionCost) {
    this.productionCost = productionCost;
  }

  public List<Integer> getRecipeIds() {
    return recipeIds;
  }

  public void setRecipeIds(List<Integer> recipeIds) {
    this.recipeIds = recipeIds;
  }

  @Override
  public String toString() {
    return "PRODUCT_GQLInput{name='" + name + "', description='" + description + "', categoryId='" + categoryId + "', productionCost='" + productionCost + "', recipeIds='" + recipeIds + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PRODUCT_GQLInput that = (PRODUCT_GQLInput) o;
    return Objects.equals(name, that.name) &&
        Objects.equals(description, that.description) &&
        categoryId == that.categoryId &&
        productionCost == that.productionCost &&
        Objects.equals(recipeIds, that.recipeIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, categoryId, productionCost, recipeIds);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.graphql.generated.Generated
  public static class Builder {
    private String name;

    private String description;

    private int categoryId;

    private double productionCost;

    private List<Integer> recipeIds;

    public PRODUCT_GQLInput build() {
      PRODUCT_GQLInput result = new PRODUCT_GQLInput();
      result.name = this.name;
      result.description = this.description;
      result.categoryId = this.categoryId;
      result.productionCost = this.productionCost;
      result.recipeIds = this.recipeIds;
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

    public Builder categoryId(int categoryId) {
      this.categoryId = categoryId;
      return this;
    }

    public Builder productionCost(double productionCost) {
      this.productionCost = productionCost;
      return this;
    }

    public Builder recipeIds(List<Integer> recipeIds) {
      this.recipeIds = recipeIds;
      return this;
    }
  }
}

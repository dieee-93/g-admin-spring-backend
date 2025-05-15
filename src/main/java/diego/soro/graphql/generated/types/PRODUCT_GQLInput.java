package diego.soro.graphql.generated.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.graphql.generated.Generated
public class PRODUCT_GQLInput {
  private String name;

  private String description;

  private String categoryId;

  private BigDecimal productionCost;

  private BigDecimal sellingPrice;

  private List<RECIPE_ITEM_GQLInput> recipe;

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

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public BigDecimal getProductionCost() {
    return productionCost;
  }

  public void setProductionCost(BigDecimal productionCost) {
    this.productionCost = productionCost;
  }

  public BigDecimal getSellingPrice() {
    return sellingPrice;
  }

  public void setSellingPrice(BigDecimal sellingPrice) {
    this.sellingPrice = sellingPrice;
  }

  public List<RECIPE_ITEM_GQLInput> getRecipe() {
    return recipe;
  }

  public void setRecipe(List<RECIPE_ITEM_GQLInput> recipe) {
    this.recipe = recipe;
  }

  @Override
  public String toString() {
    return "PRODUCT_GQLInput{name='" + name + "', description='" + description + "', categoryId='" + categoryId + "', productionCost='" + productionCost + "', sellingPrice='" + sellingPrice + "', recipe='" + recipe + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PRODUCT_GQLInput that = (PRODUCT_GQLInput) o;
    return Objects.equals(name, that.name) &&
        Objects.equals(description, that.description) &&
        Objects.equals(categoryId, that.categoryId) &&
        Objects.equals(productionCost, that.productionCost) &&
        Objects.equals(sellingPrice, that.sellingPrice) &&
        Objects.equals(recipe, that.recipe);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, categoryId, productionCost, sellingPrice, recipe);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.graphql.generated.Generated
  public static class Builder {
    private String name;

    private String description;

    private String categoryId;

    private BigDecimal productionCost;

    private BigDecimal sellingPrice;

    private List<RECIPE_ITEM_GQLInput> recipe;

    public PRODUCT_GQLInput build() {
      PRODUCT_GQLInput result = new PRODUCT_GQLInput();
      result.name = this.name;
      result.description = this.description;
      result.categoryId = this.categoryId;
      result.productionCost = this.productionCost;
      result.sellingPrice = this.sellingPrice;
      result.recipe = this.recipe;
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

    public Builder categoryId(String categoryId) {
      this.categoryId = categoryId;
      return this;
    }

    public Builder productionCost(BigDecimal productionCost) {
      this.productionCost = productionCost;
      return this;
    }

    public Builder sellingPrice(BigDecimal sellingPrice) {
      this.sellingPrice = sellingPrice;
      return this;
    }

    public Builder recipe(List<RECIPE_ITEM_GQLInput> recipe) {
      this.recipe = recipe;
      return this;
    }
  }
}

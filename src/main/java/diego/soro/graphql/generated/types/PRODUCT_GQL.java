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
public class PRODUCT_GQL {
  private String id;

  private String name;

  private String description;

  private CATEGORY_GQL category;

  private BigDecimal productionCost;

  private BigDecimal sellingPrice;

  private String measurementUnit;

  private List<RECIPE_ITEM_GQL> recipe;

  public PRODUCT_GQL() {
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

  public CATEGORY_GQL getCategory() {
    return category;
  }

  public void setCategory(CATEGORY_GQL category) {
    this.category = category;
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

  public String getMeasurementUnit() {
    return measurementUnit;
  }

  public void setMeasurementUnit(String measurementUnit) {
    this.measurementUnit = measurementUnit;
  }

  public List<RECIPE_ITEM_GQL> getRecipe() {
    return recipe;
  }

  public void setRecipe(List<RECIPE_ITEM_GQL> recipe) {
    this.recipe = recipe;
  }

  @Override
  public String toString() {
    return "PRODUCT_GQL{id='" + id + "', name='" + name + "', description='" + description + "', category='" + category + "', productionCost='" + productionCost + "', sellingPrice='" + sellingPrice + "', measurementUnit='" + measurementUnit + "', recipe='" + recipe + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PRODUCT_GQL that = (PRODUCT_GQL) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(name, that.name) &&
        Objects.equals(description, that.description) &&
        Objects.equals(category, that.category) &&
        Objects.equals(productionCost, that.productionCost) &&
        Objects.equals(sellingPrice, that.sellingPrice) &&
        Objects.equals(measurementUnit, that.measurementUnit) &&
        Objects.equals(recipe, that.recipe);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, category, productionCost, sellingPrice, measurementUnit, recipe);
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

    private CATEGORY_GQL category;

    private BigDecimal productionCost;

    private BigDecimal sellingPrice;

    private String measurementUnit;

    private List<RECIPE_ITEM_GQL> recipe;

    public PRODUCT_GQL build() {
      PRODUCT_GQL result = new PRODUCT_GQL();
      result.id = this.id;
      result.name = this.name;
      result.description = this.description;
      result.category = this.category;
      result.productionCost = this.productionCost;
      result.sellingPrice = this.sellingPrice;
      result.measurementUnit = this.measurementUnit;
      result.recipe = this.recipe;
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

    public Builder category(CATEGORY_GQL category) {
      this.category = category;
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

    public Builder measurementUnit(String measurementUnit) {
      this.measurementUnit = measurementUnit;
      return this;
    }

    public Builder recipe(List<RECIPE_ITEM_GQL> recipe) {
      this.recipe = recipe;
      return this;
    }
  }
}

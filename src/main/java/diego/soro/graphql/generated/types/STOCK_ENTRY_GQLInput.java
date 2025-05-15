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
public class STOCK_ENTRY_GQLInput {
  private String rawMaterialId;

  private BigDecimal quantity;

  private BigDecimal cost;

  private String measurementUnit;

  private List<RECIPE_ITEM_GQLInput> recipe;

  public STOCK_ENTRY_GQLInput() {
  }

  public String getRawMaterialId() {
    return rawMaterialId;
  }

  public void setRawMaterialId(String rawMaterialId) {
    this.rawMaterialId = rawMaterialId;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

  public String getMeasurementUnit() {
    return measurementUnit;
  }

  public void setMeasurementUnit(String measurementUnit) {
    this.measurementUnit = measurementUnit;
  }

  public List<RECIPE_ITEM_GQLInput> getRecipe() {
    return recipe;
  }

  public void setRecipe(List<RECIPE_ITEM_GQLInput> recipe) {
    this.recipe = recipe;
  }

  @Override
  public String toString() {
    return "STOCK_ENTRY_GQLInput{rawMaterialId='" + rawMaterialId + "', quantity='" + quantity + "', cost='" + cost + "', measurementUnit='" + measurementUnit + "', recipe='" + recipe + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    STOCK_ENTRY_GQLInput that = (STOCK_ENTRY_GQLInput) o;
    return Objects.equals(rawMaterialId, that.rawMaterialId) &&
        Objects.equals(quantity, that.quantity) &&
        Objects.equals(cost, that.cost) &&
        Objects.equals(measurementUnit, that.measurementUnit) &&
        Objects.equals(recipe, that.recipe);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rawMaterialId, quantity, cost, measurementUnit, recipe);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.graphql.generated.Generated
  public static class Builder {
    private String rawMaterialId;

    private BigDecimal quantity;

    private BigDecimal cost;

    private String measurementUnit;

    private List<RECIPE_ITEM_GQLInput> recipe;

    public STOCK_ENTRY_GQLInput build() {
      STOCK_ENTRY_GQLInput result = new STOCK_ENTRY_GQLInput();
      result.rawMaterialId = this.rawMaterialId;
      result.quantity = this.quantity;
      result.cost = this.cost;
      result.measurementUnit = this.measurementUnit;
      result.recipe = this.recipe;
      return result;
    }

    public Builder rawMaterialId(String rawMaterialId) {
      this.rawMaterialId = rawMaterialId;
      return this;
    }

    public Builder quantity(BigDecimal quantity) {
      this.quantity = quantity;
      return this;
    }

    public Builder cost(BigDecimal cost) {
      this.cost = cost;
      return this;
    }

    public Builder measurementUnit(String measurementUnit) {
      this.measurementUnit = measurementUnit;
      return this;
    }

    public Builder recipe(List<RECIPE_ITEM_GQLInput> recipe) {
      this.recipe = recipe;
      return this;
    }
  }
}

package diego.soro.g-admin.generated.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.g-admin.generated.Generated
public class RawMaterialInput {
  private String name;

  private int categoryId;

  private String rawMaterialType;

  private double cost;

  private double quantity;

  private String measurementUnit;

  private String recipe;

  public RawMaterialInput() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public String getRawMaterialType() {
    return rawMaterialType;
  }

  public void setRawMaterialType(String rawMaterialType) {
    this.rawMaterialType = rawMaterialType;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  public String getMeasurementUnit() {
    return measurementUnit;
  }

  public void setMeasurementUnit(String measurementUnit) {
    this.measurementUnit = measurementUnit;
  }

  public String getRecipe() {
    return recipe;
  }

  public void setRecipe(String recipe) {
    this.recipe = recipe;
  }

  @Override
  public String toString() {
    return "RawMaterialInput{name='" + name + "', categoryId='" + categoryId + "', rawMaterialType='" + rawMaterialType + "', cost='" + cost + "', quantity='" + quantity + "', measurementUnit='" + measurementUnit + "', recipe='" + recipe + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RawMaterialInput that = (RawMaterialInput) o;
    return Objects.equals(name, that.name) &&
        categoryId == that.categoryId &&
        Objects.equals(rawMaterialType, that.rawMaterialType) &&
        cost == that.cost &&
        quantity == that.quantity &&
        Objects.equals(measurementUnit, that.measurementUnit) &&
        Objects.equals(recipe, that.recipe);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, categoryId, rawMaterialType, cost, quantity, measurementUnit, recipe);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.g-admin.generated.Generated
  public static class Builder {
    private String name;

    private int categoryId;

    private String rawMaterialType;

    private double cost;

    private double quantity;

    private String measurementUnit;

    private String recipe;

    public RawMaterialInput build() {
      RawMaterialInput result = new RawMaterialInput();
      result.name = this.name;
      result.categoryId = this.categoryId;
      result.rawMaterialType = this.rawMaterialType;
      result.cost = this.cost;
      result.quantity = this.quantity;
      result.measurementUnit = this.measurementUnit;
      result.recipe = this.recipe;
      return result;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder categoryId(int categoryId) {
      this.categoryId = categoryId;
      return this;
    }

    public Builder rawMaterialType(String rawMaterialType) {
      this.rawMaterialType = rawMaterialType;
      return this;
    }

    public Builder cost(double cost) {
      this.cost = cost;
      return this;
    }

    public Builder quantity(double quantity) {
      this.quantity = quantity;
      return this;
    }

    public Builder measurementUnit(String measurementUnit) {
      this.measurementUnit = measurementUnit;
      return this;
    }

    public Builder recipe(String recipe) {
      this.recipe = recipe;
      return this;
    }
  }
}

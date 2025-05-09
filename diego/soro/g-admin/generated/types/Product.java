package diego.soro.g-admin.generated.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.g-admin.generated.Generated
public class Product {
  private String id;

  private String name;

  private int categoryId;

  private int rawMaterialId;

  private double cost;

  private double quantity;

  private String measurementUnit;

  private String recipe;

  public Product() {
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

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public int getRawMaterialId() {
    return rawMaterialId;
  }

  public void setRawMaterialId(int rawMaterialId) {
    this.rawMaterialId = rawMaterialId;
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
    return "Product{id='" + id + "', name='" + name + "', categoryId='" + categoryId + "', rawMaterialId='" + rawMaterialId + "', cost='" + cost + "', quantity='" + quantity + "', measurementUnit='" + measurementUnit + "', recipe='" + recipe + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product that = (Product) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(name, that.name) &&
        categoryId == that.categoryId &&
        rawMaterialId == that.rawMaterialId &&
        cost == that.cost &&
        quantity == that.quantity &&
        Objects.equals(measurementUnit, that.measurementUnit) &&
        Objects.equals(recipe, that.recipe);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, categoryId, rawMaterialId, cost, quantity, measurementUnit, recipe);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.g-admin.generated.Generated
  public static class Builder {
    private String id;

    private String name;

    private int categoryId;

    private int rawMaterialId;

    private double cost;

    private double quantity;

    private String measurementUnit;

    private String recipe;

    public Product build() {
      Product result = new Product();
      result.id = this.id;
      result.name = this.name;
      result.categoryId = this.categoryId;
      result.rawMaterialId = this.rawMaterialId;
      result.cost = this.cost;
      result.quantity = this.quantity;
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

    public Builder categoryId(int categoryId) {
      this.categoryId = categoryId;
      return this;
    }

    public Builder rawMaterialId(int rawMaterialId) {
      this.rawMaterialId = rawMaterialId;
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

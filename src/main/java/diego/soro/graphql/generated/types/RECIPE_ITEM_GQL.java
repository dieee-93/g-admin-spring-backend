package diego.soro.graphql.generated.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.math.BigDecimal;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.graphql.generated.Generated
public class RECIPE_ITEM_GQL {
  private String id;

  private String productId;

  private String rawMaterialId;

  private BigDecimal quantity;

  private BigDecimal cost;

  private String measurementUnit;

  public RECIPE_ITEM_GQL() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
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

  @Override
  public String toString() {
    return "RECIPE_ITEM_GQL{id='" + id + "', productId='" + productId + "', rawMaterialId='" + rawMaterialId + "', quantity='" + quantity + "', cost='" + cost + "', measurementUnit='" + measurementUnit + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RECIPE_ITEM_GQL that = (RECIPE_ITEM_GQL) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(productId, that.productId) &&
        Objects.equals(rawMaterialId, that.rawMaterialId) &&
        Objects.equals(quantity, that.quantity) &&
        Objects.equals(cost, that.cost) &&
        Objects.equals(measurementUnit, that.measurementUnit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, productId, rawMaterialId, quantity, cost, measurementUnit);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.graphql.generated.Generated
  public static class Builder {
    private String id;

    private String productId;

    private String rawMaterialId;

    private BigDecimal quantity;

    private BigDecimal cost;

    private String measurementUnit;

    public RECIPE_ITEM_GQL build() {
      RECIPE_ITEM_GQL result = new RECIPE_ITEM_GQL();
      result.id = this.id;
      result.productId = this.productId;
      result.rawMaterialId = this.rawMaterialId;
      result.quantity = this.quantity;
      result.cost = this.cost;
      result.measurementUnit = this.measurementUnit;
      return result;
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder productId(String productId) {
      this.productId = productId;
      return this;
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
  }
}

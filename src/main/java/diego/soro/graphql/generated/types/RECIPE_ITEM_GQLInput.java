package diego.soro.graphql.generated.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.math.BigDecimal;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.graphql.generated.Generated
public class RECIPE_ITEM_GQLInput {
  private String productId;

  private String rawMaterialId;

  private String stockEntryId;

  private BigDecimal quantity;

  private BigDecimal cost;

  private String measurementUnit;

  public RECIPE_ITEM_GQLInput() {
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

  public String getStockEntryId() {
    return stockEntryId;
  }

  public void setStockEntryId(String stockEntryId) {
    this.stockEntryId = stockEntryId;
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
    return "RECIPE_ITEM_GQLInput{productId='" + productId + "', rawMaterialId='" + rawMaterialId + "', stockEntryId='" + stockEntryId + "', quantity='" + quantity + "', cost='" + cost + "', measurementUnit='" + measurementUnit + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RECIPE_ITEM_GQLInput that = (RECIPE_ITEM_GQLInput) o;
    return Objects.equals(productId, that.productId) &&
        Objects.equals(rawMaterialId, that.rawMaterialId) &&
        Objects.equals(stockEntryId, that.stockEntryId) &&
        Objects.equals(quantity, that.quantity) &&
        Objects.equals(cost, that.cost) &&
        Objects.equals(measurementUnit, that.measurementUnit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId, rawMaterialId, stockEntryId, quantity, cost, measurementUnit);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.graphql.generated.Generated
  public static class Builder {
    private String productId;

    private String rawMaterialId;

    private String stockEntryId;

    private BigDecimal quantity;

    private BigDecimal cost;

    private String measurementUnit;

    public RECIPE_ITEM_GQLInput build() {
      RECIPE_ITEM_GQLInput result = new RECIPE_ITEM_GQLInput();
      result.productId = this.productId;
      result.rawMaterialId = this.rawMaterialId;
      result.stockEntryId = this.stockEntryId;
      result.quantity = this.quantity;
      result.cost = this.cost;
      result.measurementUnit = this.measurementUnit;
      return result;
    }

    public Builder productId(String productId) {
      this.productId = productId;
      return this;
    }

    public Builder rawMaterialId(String rawMaterialId) {
      this.rawMaterialId = rawMaterialId;
      return this;
    }

    public Builder stockEntryId(String stockEntryId) {
      this.stockEntryId = stockEntryId;
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

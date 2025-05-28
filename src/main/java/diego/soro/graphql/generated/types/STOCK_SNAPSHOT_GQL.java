package diego.soro.graphql.generated.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.math.BigDecimal;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.graphql.generated.Generated
public class STOCK_SNAPSHOT_GQL {
  private String id;

  private RAW_MATERIAL_GQL rawMaterial;

  private BigDecimal quantity;

  private BigDecimal cost;

  private String measurementUnit;

  public STOCK_SNAPSHOT_GQL() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public RAW_MATERIAL_GQL getRawMaterial() {
    return rawMaterial;
  }

  public void setRawMaterial(RAW_MATERIAL_GQL rawMaterial) {
    this.rawMaterial = rawMaterial;
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
    return "STOCK_SNAPSHOT_GQL{id='" + id + "', rawMaterial='" + rawMaterial + "', quantity='" + quantity + "', cost='" + cost + "', measurementUnit='" + measurementUnit + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    STOCK_SNAPSHOT_GQL that = (STOCK_SNAPSHOT_GQL) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(rawMaterial, that.rawMaterial) &&
        Objects.equals(quantity, that.quantity) &&
        Objects.equals(cost, that.cost) &&
        Objects.equals(measurementUnit, that.measurementUnit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, rawMaterial, quantity, cost, measurementUnit);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.graphql.generated.Generated
  public static class Builder {
    private String id;

    private RAW_MATERIAL_GQL rawMaterial;

    private BigDecimal quantity;

    private BigDecimal cost;

    private String measurementUnit;

    public STOCK_SNAPSHOT_GQL build() {
      STOCK_SNAPSHOT_GQL result = new STOCK_SNAPSHOT_GQL();
      result.id = this.id;
      result.rawMaterial = this.rawMaterial;
      result.quantity = this.quantity;
      result.cost = this.cost;
      result.measurementUnit = this.measurementUnit;
      return result;
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder rawMaterial(RAW_MATERIAL_GQL rawMaterial) {
      this.rawMaterial = rawMaterial;
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

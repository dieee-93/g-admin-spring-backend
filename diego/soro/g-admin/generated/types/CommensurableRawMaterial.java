package diego.soro.g-admin.generated.types;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.g-admin.generated.Generated
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NONE
)
public class CommensurableRawMaterial implements diego.soro.g-admin.generated.types.RawMaterial {
  private String id;

  private String name;

  private int categoryId;

  private String rawMaterialType;

  private double cost;

  private double quantity;

  private String measurementUnit;

  public CommensurableRawMaterial() {
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

  @Override
  public String toString() {
    return "CommensurableRawMaterial{id='" + id + "', name='" + name + "', categoryId='" + categoryId + "', rawMaterialType='" + rawMaterialType + "', cost='" + cost + "', quantity='" + quantity + "', measurementUnit='" + measurementUnit + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CommensurableRawMaterial that = (CommensurableRawMaterial) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(name, that.name) &&
        categoryId == that.categoryId &&
        Objects.equals(rawMaterialType, that.rawMaterialType) &&
        cost == that.cost &&
        quantity == that.quantity &&
        Objects.equals(measurementUnit, that.measurementUnit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, categoryId, rawMaterialType, cost, quantity, measurementUnit);
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

    private String rawMaterialType;

    private double cost;

    private double quantity;

    private String measurementUnit;

    public CommensurableRawMaterial build() {
      CommensurableRawMaterial result = new CommensurableRawMaterial();
      result.id = this.id;
      result.name = this.name;
      result.categoryId = this.categoryId;
      result.rawMaterialType = this.rawMaterialType;
      result.cost = this.cost;
      result.quantity = this.quantity;
      result.measurementUnit = this.measurementUnit;
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
  }
}

package diego.soro.g-admin.generated.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.g-admin.generated.Generated
public class StockEntryInput {
  private String rawMaterialId;

  private double quantity;

  private double cost;

  private String reason;

  private String timestamp;

  private String operationType;

  private String notes;

  public StockEntryInput() {
  }

  public String getRawMaterialId() {
    return rawMaterialId;
  }

  public void setRawMaterialId(String rawMaterialId) {
    this.rawMaterialId = rawMaterialId;
  }

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getOperationType() {
    return operationType;
  }

  public void setOperationType(String operationType) {
    this.operationType = operationType;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  @Override
  public String toString() {
    return "StockEntryInput{rawMaterialId='" + rawMaterialId + "', quantity='" + quantity + "', cost='" + cost + "', reason='" + reason + "', timestamp='" + timestamp + "', operationType='" + operationType + "', notes='" + notes + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StockEntryInput that = (StockEntryInput) o;
    return Objects.equals(rawMaterialId, that.rawMaterialId) &&
        quantity == that.quantity &&
        cost == that.cost &&
        Objects.equals(reason, that.reason) &&
        Objects.equals(timestamp, that.timestamp) &&
        Objects.equals(operationType, that.operationType) &&
        Objects.equals(notes, that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rawMaterialId, quantity, cost, reason, timestamp, operationType, notes);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.g-admin.generated.Generated
  public static class Builder {
    private String rawMaterialId;

    private double quantity;

    private double cost;

    private String reason;

    private String timestamp;

    private String operationType;

    private String notes;

    public StockEntryInput build() {
      StockEntryInput result = new StockEntryInput();
      result.rawMaterialId = this.rawMaterialId;
      result.quantity = this.quantity;
      result.cost = this.cost;
      result.reason = this.reason;
      result.timestamp = this.timestamp;
      result.operationType = this.operationType;
      result.notes = this.notes;
      return result;
    }

    public Builder rawMaterialId(String rawMaterialId) {
      this.rawMaterialId = rawMaterialId;
      return this;
    }

    public Builder quantity(double quantity) {
      this.quantity = quantity;
      return this;
    }

    public Builder cost(double cost) {
      this.cost = cost;
      return this;
    }

    public Builder reason(String reason) {
      this.reason = reason;
      return this;
    }

    public Builder timestamp(String timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public Builder operationType(String operationType) {
      this.operationType = operationType;
      return this;
    }

    public Builder notes(String notes) {
      this.notes = notes;
      return this;
    }
  }
}

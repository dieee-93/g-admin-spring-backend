package diego.soro.graphql.generated.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.graphql.generated.Generated
public class CREATE_PRODUCT_RESPONSE {
  /**
   * Similar to HTTP status code, represents the status of the mutation
   */
  private int code;

  /**
   * Indicates whether the mutation was successful
   */
  private boolean success;

  /**
   * Human-readable message for the UI
   */
  private String message;

  /**
   * The newly created listing
   */
  private PRODUCT_GQL product;

  public CREATE_PRODUCT_RESPONSE() {
  }

  /**
   * Similar to HTTP status code, represents the status of the mutation
   */
  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  /**
   * Indicates whether the mutation was successful
   */
  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  /**
   * Human-readable message for the UI
   */
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * The newly created listing
   */
  public PRODUCT_GQL getProduct() {
    return product;
  }

  public void setProduct(PRODUCT_GQL product) {
    this.product = product;
  }

  @Override
  public String toString() {
    return "CREATE_PRODUCT_RESPONSE{code='" + code + "', success='" + success + "', message='" + message + "', product='" + product + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CREATE_PRODUCT_RESPONSE that = (CREATE_PRODUCT_RESPONSE) o;
    return code == that.code &&
        success == that.success &&
        Objects.equals(message, that.message) &&
        Objects.equals(product, that.product);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, success, message, product);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.graphql.generated.Generated
  public static class Builder {
    /**
     * Similar to HTTP status code, represents the status of the mutation
     */
    private int code;

    /**
     * Indicates whether the mutation was successful
     */
    private boolean success;

    /**
     * Human-readable message for the UI
     */
    private String message;

    /**
     * The newly created listing
     */
    private PRODUCT_GQL product;

    public CREATE_PRODUCT_RESPONSE build() {
      CREATE_PRODUCT_RESPONSE result = new CREATE_PRODUCT_RESPONSE();
      result.code = this.code;
      result.success = this.success;
      result.message = this.message;
      result.product = this.product;
      return result;
    }

    /**
     * Similar to HTTP status code, represents the status of the mutation
     */
    public Builder code(int code) {
      this.code = code;
      return this;
    }

    /**
     * Indicates whether the mutation was successful
     */
    public Builder success(boolean success) {
      this.success = success;
      return this;
    }

    /**
     * Human-readable message for the UI
     */
    public Builder message(String message) {
      this.message = message;
      return this;
    }

    /**
     * The newly created listing
     */
    public Builder product(PRODUCT_GQL product) {
      this.product = product;
      return this;
    }
  }
}

package diego.soro.g-admin.generated.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.g-admin.generated.Generated
public class CreateRawMaterialResponse {
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
  private RawMaterial rawMaterial;

  public CreateRawMaterialResponse() {
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
  public RawMaterial getRawMaterial() {
    return rawMaterial;
  }

  public void setRawMaterial(RawMaterial rawMaterial) {
    this.rawMaterial = rawMaterial;
  }

  @Override
  public String toString() {
    return "CreateRawMaterialResponse{code='" + code + "', success='" + success + "', message='" + message + "', rawMaterial='" + rawMaterial + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CreateRawMaterialResponse that = (CreateRawMaterialResponse) o;
    return code == that.code &&
        success == that.success &&
        Objects.equals(message, that.message) &&
        Objects.equals(rawMaterial, that.rawMaterial);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, success, message, rawMaterial);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.g-admin.generated.Generated
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
    private RawMaterial rawMaterial;

    public CreateRawMaterialResponse build() {
      CreateRawMaterialResponse result = new CreateRawMaterialResponse();
      result.code = this.code;
      result.success = this.success;
      result.message = this.message;
      result.rawMaterial = this.rawMaterial;
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
    public Builder rawMaterial(RawMaterial rawMaterial) {
      this.rawMaterial = rawMaterial;
      return this;
    }
  }
}

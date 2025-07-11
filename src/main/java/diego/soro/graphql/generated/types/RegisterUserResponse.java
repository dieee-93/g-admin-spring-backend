package diego.soro.graphql.generated.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.graphql.generated.Generated
public class RegisterUserResponse {
  private String status;

  private String message;

  public RegisterUserResponse() {
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "RegisterUserResponse{status='" + status + "', message='" + message + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RegisterUserResponse that = (RegisterUserResponse) o;
    return Objects.equals(status, that.status) &&
        Objects.equals(message, that.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, message);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.graphql.generated.Generated
  public static class Builder {
    private String status;

    private String message;

    public RegisterUserResponse build() {
      RegisterUserResponse result = new RegisterUserResponse();
      result.status = this.status;
      result.message = this.message;
      return result;
    }

    public Builder status(String status) {
      this.status = status;
      return this;
    }

    public Builder message(String message) {
      this.message = message;
      return this;
    }
  }
}

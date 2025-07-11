package diego.soro.graphql.generated.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.graphql.generated.Generated
public class CreateUserInput {
  private String email;

  private String password;

  private String firstName;

  private String lastName;

  private String fullAddress;

  private String zipCode;

  private String phone;

  private String registerDate;

  private List<String> roles;

  public CreateUserInput() {
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFullAddress() {
    return fullAddress;
  }

  public void setFullAddress(String fullAddress) {
    this.fullAddress = fullAddress;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getRegisterDate() {
    return registerDate;
  }

  public void setRegisterDate(String registerDate) {
    this.registerDate = registerDate;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  @Override
  public String toString() {
    return "CreateUserInput{email='" + email + "', password='" + password + "', firstName='" + firstName + "', lastName='" + lastName + "', fullAddress='" + fullAddress + "', zipCode='" + zipCode + "', phone='" + phone + "', registerDate='" + registerDate + "', roles='" + roles + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CreateUserInput that = (CreateUserInput) o;
    return Objects.equals(email, that.email) &&
        Objects.equals(password, that.password) &&
        Objects.equals(firstName, that.firstName) &&
        Objects.equals(lastName, that.lastName) &&
        Objects.equals(fullAddress, that.fullAddress) &&
        Objects.equals(zipCode, that.zipCode) &&
        Objects.equals(phone, that.phone) &&
        Objects.equals(registerDate, that.registerDate) &&
        Objects.equals(roles, that.roles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, password, firstName, lastName, fullAddress, zipCode, phone, registerDate, roles);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.graphql.generated.Generated
  public static class Builder {
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String fullAddress;

    private String zipCode;

    private String phone;

    private String registerDate;

    private List<String> roles;

    public CreateUserInput build() {
      CreateUserInput result = new CreateUserInput();
      result.email = this.email;
      result.password = this.password;
      result.firstName = this.firstName;
      result.lastName = this.lastName;
      result.fullAddress = this.fullAddress;
      result.zipCode = this.zipCode;
      result.phone = this.phone;
      result.registerDate = this.registerDate;
      result.roles = this.roles;
      return result;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder password(String password) {
      this.password = password;
      return this;
    }

    public Builder firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder fullAddress(String fullAddress) {
      this.fullAddress = fullAddress;
      return this;
    }

    public Builder zipCode(String zipCode) {
      this.zipCode = zipCode;
      return this;
    }

    public Builder phone(String phone) {
      this.phone = phone;
      return this;
    }

    public Builder registerDate(String registerDate) {
      this.registerDate = registerDate;
      return this;
    }

    public Builder roles(List<String> roles) {
      this.roles = roles;
      return this;
    }
  }
}

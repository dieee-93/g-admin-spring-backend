package diego.soro.graphql.generated.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.graphql.generated.Generated
public class CreateCompanyInput {
  private String name;

  private String taxId;

  private String email;

  private String phone;

  private String address;

  private String country;

  private String timezone;

  public CreateCompanyInput() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTaxId() {
    return taxId;
  }

  public void setTaxId(String taxId) {
    this.taxId = taxId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getTimezone() {
    return timezone;
  }

  public void setTimezone(String timezone) {
    this.timezone = timezone;
  }

  @Override
  public String toString() {
    return "CreateCompanyInput{name='" + name + "', taxId='" + taxId + "', email='" + email + "', phone='" + phone + "', address='" + address + "', country='" + country + "', timezone='" + timezone + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CreateCompanyInput that = (CreateCompanyInput) o;
    return Objects.equals(name, that.name) &&
        Objects.equals(taxId, that.taxId) &&
        Objects.equals(email, that.email) &&
        Objects.equals(phone, that.phone) &&
        Objects.equals(address, that.address) &&
        Objects.equals(country, that.country) &&
        Objects.equals(timezone, that.timezone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, taxId, email, phone, address, country, timezone);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.graphql.generated.Generated
  public static class Builder {
    private String name;

    private String taxId;

    private String email;

    private String phone;

    private String address;

    private String country;

    private String timezone;

    public CreateCompanyInput build() {
      CreateCompanyInput result = new CreateCompanyInput();
      result.name = this.name;
      result.taxId = this.taxId;
      result.email = this.email;
      result.phone = this.phone;
      result.address = this.address;
      result.country = this.country;
      result.timezone = this.timezone;
      return result;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder taxId(String taxId) {
      this.taxId = taxId;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder phone(String phone) {
      this.phone = phone;
      return this;
    }

    public Builder address(String address) {
      this.address = address;
      return this;
    }

    public Builder country(String country) {
      this.country = country;
      return this;
    }

    public Builder timezone(String timezone) {
      this.timezone = timezone;
      return this;
    }
  }
}

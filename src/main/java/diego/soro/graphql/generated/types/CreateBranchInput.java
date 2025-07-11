package diego.soro.graphql.generated.types;

import jakarta.annotation.Generated;
import java.lang.Boolean;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.graphql.generated.Generated
public class CreateBranchInput {
  private String name;

  private String code;

  private String address;

  private String phone;

  private String email;

  private Boolean isMain;

  private String companyId;

  public CreateBranchInput() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Boolean getIsMain() {
    return isMain;
  }

  public void setIsMain(Boolean isMain) {
    this.isMain = isMain;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  @Override
  public String toString() {
    return "CreateBranchInput{name='" + name + "', code='" + code + "', address='" + address + "', phone='" + phone + "', email='" + email + "', isMain='" + isMain + "', companyId='" + companyId + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CreateBranchInput that = (CreateBranchInput) o;
    return Objects.equals(name, that.name) &&
        Objects.equals(code, that.code) &&
        Objects.equals(address, that.address) &&
        Objects.equals(phone, that.phone) &&
        Objects.equals(email, that.email) &&
        Objects.equals(isMain, that.isMain) &&
        Objects.equals(companyId, that.companyId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, code, address, phone, email, isMain, companyId);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.graphql.generated.Generated
  public static class Builder {
    private String name;

    private String code;

    private String address;

    private String phone;

    private String email;

    private Boolean isMain;

    private String companyId;

    public CreateBranchInput build() {
      CreateBranchInput result = new CreateBranchInput();
      result.name = this.name;
      result.code = this.code;
      result.address = this.address;
      result.phone = this.phone;
      result.email = this.email;
      result.isMain = this.isMain;
      result.companyId = this.companyId;
      return result;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder code(String code) {
      this.code = code;
      return this;
    }

    public Builder address(String address) {
      this.address = address;
      return this;
    }

    public Builder phone(String phone) {
      this.phone = phone;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder isMain(Boolean isMain) {
      this.isMain = isMain;
      return this;
    }

    public Builder companyId(String companyId) {
      this.companyId = companyId;
      return this;
    }
  }
}

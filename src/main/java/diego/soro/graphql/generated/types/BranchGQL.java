package diego.soro.graphql.generated.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.graphql.generated.Generated
public class BranchGQL {
  private String id;

  private String name;

  private String code;

  private String address;

  private String phone;

  private String email;

  private boolean isMain;

  private CompanyGQL company;

  private String createdAt;

  private String updatedAt;

  private boolean active;

  public BranchGQL() {
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

  public boolean isIsMain() {
    return isMain;
  }

  public void setIsMain(boolean isMain) {
    this.isMain = isMain;
  }

  public CompanyGQL getCompany() {
    return company;
  }

  public void setCompany(CompanyGQL company) {
    this.company = company;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public String toString() {
    return "BranchGQL{id='" + id + "', name='" + name + "', code='" + code + "', address='" + address + "', phone='" + phone + "', email='" + email + "', isMain='" + isMain + "', company='" + company + "', createdAt='" + createdAt + "', updatedAt='" + updatedAt + "', active='" + active + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BranchGQL that = (BranchGQL) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(name, that.name) &&
        Objects.equals(code, that.code) &&
        Objects.equals(address, that.address) &&
        Objects.equals(phone, that.phone) &&
        Objects.equals(email, that.email) &&
        isMain == that.isMain &&
        Objects.equals(company, that.company) &&
        Objects.equals(createdAt, that.createdAt) &&
        Objects.equals(updatedAt, that.updatedAt) &&
        active == that.active;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, code, address, phone, email, isMain, company, createdAt, updatedAt, active);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.graphql.generated.Generated
  public static class Builder {
    private String id;

    private String name;

    private String code;

    private String address;

    private String phone;

    private String email;

    private boolean isMain;

    private CompanyGQL company;

    private String createdAt;

    private String updatedAt;

    private boolean active;

    public BranchGQL build() {
      BranchGQL result = new BranchGQL();
      result.id = this.id;
      result.name = this.name;
      result.code = this.code;
      result.address = this.address;
      result.phone = this.phone;
      result.email = this.email;
      result.isMain = this.isMain;
      result.company = this.company;
      result.createdAt = this.createdAt;
      result.updatedAt = this.updatedAt;
      result.active = this.active;
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

    public Builder isMain(boolean isMain) {
      this.isMain = isMain;
      return this;
    }

    public Builder company(CompanyGQL company) {
      this.company = company;
      return this;
    }

    public Builder createdAt(String createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Builder updatedAt(String updatedAt) {
      this.updatedAt = updatedAt;
      return this;
    }

    public Builder active(boolean active) {
      this.active = active;
      return this;
    }
  }
}

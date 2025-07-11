package diego.soro.graphql.generated;

import java.lang.String;

@jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@Generated
public class DgsConstants {
  public static final String QUERY_TYPE = "Query";

  public static final String MUTATION_TYPE = "Mutation";

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class BRANCHGQL {
    public static final String TYPE_NAME = "BranchGQL";

    public static final String Id = "id";

    public static final String Name = "name";

    public static final String Code = "code";

    public static final String Address = "address";

    public static final String Phone = "phone";

    public static final String Email = "email";

    public static final String IsMain = "isMain";

    public static final String Company = "company";

    public static final String CreatedAt = "createdAt";

    public static final String UpdatedAt = "updatedAt";

    public static final String Active = "active";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class COMPANYGQL {
    public static final String TYPE_NAME = "CompanyGQL";

    public static final String Id = "id";

    public static final String Name = "name";

    public static final String TaxId = "taxId";

    public static final String Email = "email";

    public static final String Phone = "phone";

    public static final String Address = "address";

    public static final String Country = "country";

    public static final String Timezone = "timezone";

    public static final String CreatedAt = "createdAt";

    public static final String UpdatedAt = "updatedAt";

    public static final String Active = "active";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class PERMISSIONGQL {
    public static final String TYPE_NAME = "PermissionGQL";

    public static final String Id = "id";

    public static final String Code = "code";

    public static final String Name = "name";

    public static final String Description = "description";

    public static final String IsSystem = "isSystem";

    public static final String CreatedAt = "createdAt";

    public static final String UpdatedAt = "updatedAt";

    public static final String Active = "active";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class ROLEGQL {
    public static final String TYPE_NAME = "RoleGQL";

    public static final String Id = "id";

    public static final String Name = "name";

    public static final String Description = "description";

    public static final String IsSystem = "isSystem";

    public static final String Permissions = "permissions";

    public static final String ParentRole = "parentRole";

    public static final String CreatedAt = "createdAt";

    public static final String UpdatedAt = "updatedAt";

    public static final String Active = "active";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class USERGQL {
    public static final String TYPE_NAME = "UserGQL";

    public static final String Id = "id";

    public static final String FirstName = "firstName";

    public static final String LastName = "lastName";

    public static final String Email = "email";

    public static final String Username = "username";

    public static final String KeycloakId = "keycloakId";

    public static final String Roles = "roles";

    public static final String Branches = "branches";

    public static final String Active = "active";

    public static final String CreatedAt = "createdAt";

    public static final String UpdatedAt = "updatedAt";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class CREATEUSERRESPONSE {
    public static final String TYPE_NAME = "CreateUserResponse";

    public static final String Status = "status";

    public static final String Message = "message";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class CREATEBRANCHINPUT {
    public static final String TYPE_NAME = "CreateBranchInput";

    public static final String Name = "name";

    public static final String Code = "code";

    public static final String Address = "address";

    public static final String Phone = "phone";

    public static final String Email = "email";

    public static final String IsMain = "isMain";

    public static final String CompanyId = "companyId";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class CREATECOMPANYINPUT {
    public static final String TYPE_NAME = "CreateCompanyInput";

    public static final String Name = "name";

    public static final String TaxId = "taxId";

    public static final String Email = "email";

    public static final String Phone = "phone";

    public static final String Address = "address";

    public static final String Country = "country";

    public static final String Timezone = "timezone";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class CREATEPERMISSIONINPUT {
    public static final String TYPE_NAME = "CreatePermissionInput";

    public static final String Code = "code";

    public static final String Name = "name";

    public static final String Description = "description";

    public static final String IsSystem = "isSystem";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class CREATEROLEINPUT {
    public static final String TYPE_NAME = "CreateRoleInput";

    public static final String Name = "name";

    public static final String Description = "description";

    public static final String IsSystem = "isSystem";

    public static final String ParentRoleId = "parentRoleId";

    public static final String PermissionIds = "permissionIds";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class CREATEUSERINPUT {
    public static final String TYPE_NAME = "CreateUserInput";

    public static final String Email = "email";

    public static final String Password = "password";

    public static final String FirstName = "firstName";

    public static final String LastName = "lastName";

    public static final String FullAddress = "fullAddress";

    public static final String ZipCode = "zipCode";

    public static final String Phone = "phone";

    public static final String RegisterDate = "registerDate";

    public static final String Roles = "roles";
  }
}

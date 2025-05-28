package diego.soro.graphql.generated;

import java.lang.String;

@jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@Generated
public class DgsConstants {
  public static final String QUERY_TYPE = "Query";

  public static final String MUTATION_TYPE = "Mutation";

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class CATEGORY_GQL {
    public static final String TYPE_NAME = "CATEGORY_GQL";

    public static final String Id = "id";

    public static final String Name = "name";

    public static final String Parent = "parent";

    public static final String Children = "children";

    public static final String Products = "products";

    public static final String RawMaterials = "rawMaterials";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class RAW_MATERIAL_GQL {
    public static final String TYPE_NAME = "RAW_MATERIAL_GQL";

    public static final String Id = "id";

    public static final String Name = "name";

    public static final String Category = "category";

    public static final String RawMaterialType = "rawMaterialType";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class QUERY {
    public static final String TYPE_NAME = "Query";

    public static final String FindAllRawMaterials = "findAllRawMaterials";

    public static final String FindRawMaterialById = "findRawMaterialById";

    public static final String GetRawMaterialCount = "getRawMaterialCount";

    public static final String FindAllCategories = "findAllCategories";

    public static final String FindAllRawMaterialCategories = "findAllRawMaterialCategories";

    public static final String FindAllProductCategories = "findAllProductCategories";

    public static final String FindCategoryById = "findCategoryById";

    public static final String GetRawMaterialCategoryCount = "getRawMaterialCategoryCount";

    public static final String GetProductCategoryCount = "getProductCategoryCount";

    public static final String GetCategoryCount = "getCategoryCount";

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class FINDRAWMATERIALBYID_INPUT_ARGUMENT {
      public static final String Id = "id";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class FINDCATEGORYBYID_INPUT_ARGUMENT {
      public static final String Id = "id";
    }
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class MUTATION {
    public static final String TYPE_NAME = "Mutation";

    public static final String CreateRawMaterial = "createRawMaterial";

    public static final String UpdateRawMaterial = "updateRawMaterial";

    public static final String RemoveRawMaterial = "removeRawMaterial";

    public static final String CreateCategory = "createCategory";

    public static final String UpdateCategory = "updateCategory";

    public static final String DeleteCategory = "deleteCategory";

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class CREATERAWMATERIAL_INPUT_ARGUMENT {
      public static final String RawMaterial = "rawMaterial";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class UPDATERAWMATERIAL_INPUT_ARGUMENT {
      public static final String Id = "id";

      public static final String RawMaterial = "rawMaterial";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class REMOVERAWMATERIAL_INPUT_ARGUMENT {
      public static final String Id = "id";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class CREATECATEGORY_INPUT_ARGUMENT {
      public static final String Category = "category";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class UPDATECATEGORY_INPUT_ARGUMENT {
      public static final String Id = "id";

      public static final String Category = "category";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class DELETECATEGORY_INPUT_ARGUMENT {
      public static final String Id = "id";
    }
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class CATEGORY_GQLINPUT {
    public static final String TYPE_NAME = "CATEGORY_GQLInput";

    public static final String Name = "name";

    public static final String ParentId = "parentId";

    public static final String ChildrenIds = "childrenIds";

    public static final String ProductsIds = "productsIds";

    public static final String RawMaterialsIds = "rawMaterialsIds";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class RAW_MATERIAL_GQLINPUT {
    public static final String TYPE_NAME = "RAW_MATERIAL_GQLInput";

    public static final String Name = "name";

    public static final String Category = "category";

    public static final String RawMaterialType = "rawMaterialType";
  }
}

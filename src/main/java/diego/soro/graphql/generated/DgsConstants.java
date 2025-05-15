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

    public static final String Type = "type";

    public static final String Parent = "parent";

    public static final String Children = "children";

    public static final String Products = "products";

    public static final String RawMaterials = "rawMaterials";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class CREATE_CATEGORY_RESPONSE {
    public static final String TYPE_NAME = "CREATE_CATEGORY_RESPONSE";

    public static final String Code = "code";

    public static final String Success = "success";

    public static final String Message = "message";

    public static final String Category = "category";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class PRODUCT_GQL {
    public static final String TYPE_NAME = "PRODUCT_GQL";

    public static final String Id = "id";

    public static final String Name = "name";

    public static final String Description = "description";

    public static final String Category = "category";

    public static final String ProductionCost = "productionCost";

    public static final String SellingPrice = "sellingPrice";

    public static final String MeasurementUnit = "measurementUnit";

    public static final String Recipe = "recipe";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class CREATE_PRODUCT_RESPONSE {
    public static final String TYPE_NAME = "CREATE_PRODUCT_RESPONSE";

    public static final String Code = "code";

    public static final String Success = "success";

    public static final String Message = "message";

    public static final String Product = "product";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class CREATE_RAW_MATERIAL_RESPONSE {
    public static final String TYPE_NAME = "CREATE_RAW_MATERIAL_RESPONSE";

    public static final String Code = "code";

    public static final String Success = "success";

    public static final String Message = "message";

    public static final String RawMaterial = "rawMaterial";
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

    public static final String GetRawMaterials = "getRawMaterials";

    public static final String FindRawMaterialById = "findRawMaterialById";

    public static final String GetRawMaterialCount = "getRawMaterialCount";

    public static final String FindAll = "findAll";

    public static final String FindAllRawMaterialCategory = "findAllRawMaterialCategory";

    public static final String FindAllProductCategory = "findAllProductCategory";

    public static final String FindCategoryById = "findCategoryById";

    public static final String GetRawMaterialCategoryCount = "getRawMaterialCategoryCount";

    public static final String GetProductCategoryCount = "getProductCategoryCount";

    public static final String GetCategoryCount = "getCategoryCount";

    public static final String GetProducts = "getProducts";

    public static final String FindProductById = "findProductById";

    public static final String GetProductCount = "getProductCount";

    public static final String GetRecipeItems = "getRecipeItems";

    public static final String FindRecipeItemById = "findRecipeItemById";

    public static final String GetRecipeItemCount = "getRecipeItemCount";

    public static final String GetStockEntries = "getStockEntries";

    public static final String FindStockEntryById = "findStockEntryById";

    public static final String GetStockEntryCount = "getStockEntryCount";

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

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class FINDPRODUCTBYID_INPUT_ARGUMENT {
      public static final String Id = "id";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class FINDRECIPEITEMBYID_INPUT_ARGUMENT {
      public static final String Id = "id";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class FINDSTOCKENTRYBYID_INPUT_ARGUMENT {
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

    public static final String CreateProduct = "createProduct";

    public static final String UpdateProduct = "updateProduct";

    public static final String DeleteProduct = "deleteProduct";

    public static final String CreateRecipeItem = "createRecipeItem";

    public static final String UpdateRecipeItem = "updateRecipeItem";

    public static final String DeleteRecipeItem = "deleteRecipeItem";

    public static final String CreateStockEntry = "createStockEntry";

    public static final String UpdateStockEntry = "updateStockEntry";

    public static final String DeleteStockEntry = "deleteStockEntry";

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

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class CREATEPRODUCT_INPUT_ARGUMENT {
      public static final String Product = "product";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class UPDATEPRODUCT_INPUT_ARGUMENT {
      public static final String Id = "id";

      public static final String Product = "product";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class DELETEPRODUCT_INPUT_ARGUMENT {
      public static final String Id = "id";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class CREATERECIPEITEM_INPUT_ARGUMENT {
      public static final String RecipeItem = "recipeItem";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class UPDATERECIPEITEM_INPUT_ARGUMENT {
      public static final String Id = "id";

      public static final String RecipeItem = "recipeItem";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class DELETERECIPEITEM_INPUT_ARGUMENT {
      public static final String Id = "id";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class CREATESTOCKENTRY_INPUT_ARGUMENT {
      public static final String Stock = "stock";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class UPDATESTOCKENTRY_INPUT_ARGUMENT {
      public static final String Id = "id";

      public static final String Stock = "stock";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class DELETESTOCKENTRY_INPUT_ARGUMENT {
      public static final String Id = "id";
    }
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class RECIPE_ITEM_GQL {
    public static final String TYPE_NAME = "RECIPE_ITEM_GQL";

    public static final String Id = "id";

    public static final String Product = "product";

    public static final String RawMaterial = "rawMaterial";

    public static final String Quantity = "quantity";

    public static final String Cost = "cost";

    public static final String MeasurementUnit = "measurementUnit";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class CREATE_RECIPE_ITEM_RESPONSE {
    public static final String TYPE_NAME = "CREATE_RECIPE_ITEM_RESPONSE";

    public static final String Code = "code";

    public static final String Success = "success";

    public static final String Message = "message";

    public static final String RecipeItem = "recipeItem";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class STOCK_ENTRY_GQL {
    public static final String TYPE_NAME = "STOCK_ENTRY_GQL";

    public static final String Id = "id";

    public static final String RawMaterial = "rawMaterial";

    public static final String Quantity = "quantity";

    public static final String Cost = "cost";

    public static final String Recipe = "recipe";

    public static final String Timestamp = "timestamp";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class CREATE_STOCK_ENTRY_RESPONSE {
    public static final String TYPE_NAME = "CREATE_STOCK_ENTRY_RESPONSE";

    public static final String Code = "code";

    public static final String Success = "success";

    public static final String Message = "message";

    public static final String Stock = "stock";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class CATEGORY_GQLINPUT {
    public static final String TYPE_NAME = "CATEGORY_GQLInput";

    public static final String Name = "name";

    public static final String Type = "type";

    public static final String ParentId = "parentId";

    public static final String ChildrenIds = "childrenIds";

    public static final String ProductsIds = "productsIds";

    public static final String RawMaterialsIds = "rawMaterialsIds";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class PRODUCT_GQLINPUT {
    public static final String TYPE_NAME = "PRODUCT_GQLInput";

    public static final String Name = "name";

    public static final String Description = "description";

    public static final String CategoryId = "categoryId";

    public static final String ProductionCost = "productionCost";

    public static final String SellingPrice = "sellingPrice";

    public static final String Recipe = "recipe";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class RAW_MATERIAL_GQLINPUT {
    public static final String TYPE_NAME = "RAW_MATERIAL_GQLInput";

    public static final String Name = "name";

    public static final String Category = "category";

    public static final String RawMaterialType = "rawMaterialType";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class RECIPE_ITEM_GQLINPUT {
    public static final String TYPE_NAME = "RECIPE_ITEM_GQLInput";

    public static final String ProductId = "productId";

    public static final String RawMaterialId = "rawMaterialId";

    public static final String Quantity = "quantity";

    public static final String Cost = "cost";

    public static final String MeasurementUnit = "measurementUnit";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class STOCK_ENTRY_GQLINPUT {
    public static final String TYPE_NAME = "STOCK_ENTRY_GQLInput";

    public static final String RawMaterialId = "rawMaterialId";

    public static final String Quantity = "quantity";

    public static final String Cost = "cost";

    public static final String MeasurementUnit = "measurementUnit";

    public static final String Recipe = "recipe";
  }
}

package diego.soro.g-admin.generated;

import java.lang.String;

@jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@Generated
public class DgsConstants {
  public static final String QUERY_TYPE = "Query";

  public static final String MUTATION_TYPE = "Mutation";

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class PRODUCTCATEGORY {
    public static final String TYPE_NAME = "ProductCategory";

    public static final String Id = "id";

    public static final String FatherCategoryId = "fatherCategoryId";

    public static final String Name = "name";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class RAWMATERIALCATEGORY {
    public static final String TYPE_NAME = "RawMaterialCategory";

    public static final String Id = "id";

    public static final String FatherCategoryId = "fatherCategoryId";

    public static final String Name = "name";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class CREATEPRODUCTCATEGORYRESPONSE {
    public static final String TYPE_NAME = "CreateProductCategoryResponse";

    public static final String Code = "code";

    public static final String Success = "success";

    public static final String Message = "message";

    public static final String ProductCategory = "productCategory";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class PRODUCT {
    public static final String TYPE_NAME = "Product";

    public static final String Id = "id";

    public static final String Name = "name";

    public static final String CategoryId = "categoryId";

    public static final String RawMaterialId = "rawMaterialId";

    public static final String Cost = "cost";

    public static final String Quantity = "quantity";

    public static final String MeasurementUnit = "measurementUnit";

    public static final String Recipe = "recipe";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class CREATEPRODUCTRESPONSE {
    public static final String TYPE_NAME = "CreateProductResponse";

    public static final String Code = "code";

    public static final String Success = "success";

    public static final String Message = "message";

    public static final String Product = "product";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class CREATERAWMATERIALRESPONSE {
    public static final String TYPE_NAME = "CreateRawMaterialResponse";

    public static final String Code = "code";

    public static final String Success = "success";

    public static final String Message = "message";

    public static final String RawMaterial = "rawMaterial";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class COMMENSURABLERAWMATERIAL {
    public static final String TYPE_NAME = "CommensurableRawMaterial";

    public static final String Id = "id";

    public static final String Name = "name";

    public static final String CategoryId = "categoryId";

    public static final String RawMaterialType = "rawMaterialType";

    public static final String Cost = "cost";

    public static final String Quantity = "quantity";

    public static final String MeasurementUnit = "measurementUnit";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class ACCOUNTINGRAWMATERIAL {
    public static final String TYPE_NAME = "AccountingRawMaterial";

    public static final String Id = "id";

    public static final String Name = "name";

    public static final String CategoryId = "categoryId";

    public static final String RawMaterialType = "rawMaterialType";

    public static final String Cost = "cost";

    public static final String Quantity = "quantity";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class PROCESSEDRAWMATERIAL {
    public static final String TYPE_NAME = "ProcessedRawMaterial";

    public static final String Id = "id";

    public static final String Name = "name";

    public static final String CategoryId = "categoryId";

    public static final String RawMaterialType = "rawMaterialType";

    public static final String Cost = "cost";

    public static final String Quantity = "quantity";

    public static final String MeasurementUnit = "measurementUnit";

    public static final String Recipe = "recipe";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class QUERY {
    public static final String TYPE_NAME = "Query";

    public static final String GetRawMaterials = "getRawMaterials";

    public static final String FindRawMaterialById = "findRawMaterialById";

    public static final String GetRawMaterialCount = "getRawMaterialCount";

    public static final String GetProductCategories = "getProductCategories";

    public static final String FindProductCategoryById = "findProductCategoryById";

    public static final String GetProductCategoryCount = "getProductCategoryCount";

    public static final String GetRawMaterialCategories = "getRawMaterialCategories";

    public static final String FindRawMaterialCategoryById = "findRawMaterialCategoryById";

    public static final String GetRawMaterialCategoryCount = "getRawMaterialCategoryCount";

    public static final String GetProducts = "getProducts";

    public static final String FindProductById = "findProductById";

    public static final String GetProductCount = "getProductCount";

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
    public static class FINDPRODUCTCATEGORYBYID_INPUT_ARGUMENT {
      public static final String Id = "id";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class FINDRAWMATERIALCATEGORYBYID_INPUT_ARGUMENT {
      public static final String Id = "id";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class FINDPRODUCTBYID_INPUT_ARGUMENT {
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

    public static final String CreateProductCategory = "createProductCategory";

    public static final String UpdateProductCategory = "updateProductCategory";

    public static final String DeleteProductCategory = "deleteProductCategory";

    public static final String CreateRawMaterialCategory = "createRawMaterialCategory";

    public static final String UpdateRawMaterialCategory = "updateRawMaterialCategory";

    public static final String DeleteRawMaterialCategory = "deleteRawMaterialCategory";

    public static final String CreateProduct = "createProduct";

    public static final String UpdateProduct = "updateProduct";

    public static final String DeleteProduct = "deleteProduct";

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
    public static class CREATEPRODUCTCATEGORY_INPUT_ARGUMENT {
      public static final String ProductCategory = "productCategory";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class UPDATEPRODUCTCATEGORY_INPUT_ARGUMENT {
      public static final String Id = "id";

      public static final String ProductCategory = "productCategory";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class DELETEPRODUCTCATEGORY_INPUT_ARGUMENT {
      public static final String Id = "id";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class CREATERAWMATERIALCATEGORY_INPUT_ARGUMENT {
      public static final String RawMaterialCategory = "rawMaterialCategory";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class UPDATERAWMATERIALCATEGORY_INPUT_ARGUMENT {
      public static final String Id = "id";

      public static final String RawMaterialCategory = "rawMaterialCategory";
    }

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class DELETERAWMATERIALCATEGORY_INPUT_ARGUMENT {
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
  public static class STOCKENTRY {
    public static final String TYPE_NAME = "StockEntry";

    public static final String Id = "id";

    public static final String RawMaterialId = "rawMaterialId";

    public static final String Quantity = "quantity";

    public static final String Cost = "cost";

    public static final String Reason = "reason";

    public static final String Timestamp = "timestamp";

    public static final String OperationType = "operationType";

    public static final String Notes = "notes";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class CREATESTOCKENTRYRESPONSE {
    public static final String TYPE_NAME = "CreateStockEntryResponse";

    public static final String Code = "code";

    public static final String Success = "success";

    public static final String Message = "message";

    public static final String Stock = "stock";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class PRODUCTCATEGORYINPUT {
    public static final String TYPE_NAME = "ProductCategoryInput";

    public static final String FatherCategoryId = "fatherCategoryId";

    public static final String Name = "name";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class RAWMATERIALCATEGORYINPUT {
    public static final String TYPE_NAME = "RawMaterialCategoryInput";

    public static final String FatherCategoryId = "fatherCategoryId";

    public static final String Name = "name";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class PRODUCTINPUT {
    public static final String TYPE_NAME = "ProductInput";

    public static final String Name = "name";

    public static final String Description = "description";

    public static final String CategoryId = "categoryId";

    public static final String ProductionCost = "productionCost";

    public static final String RecipeIds = "recipeIds";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class RAWMATERIALINPUT {
    public static final String TYPE_NAME = "RawMaterialInput";

    public static final String Name = "name";

    public static final String CategoryId = "categoryId";

    public static final String RawMaterialType = "rawMaterialType";

    public static final String Cost = "cost";

    public static final String Quantity = "quantity";

    public static final String MeasurementUnit = "measurementUnit";

    public static final String Recipe = "recipe";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class STOCKENTRYINPUT {
    public static final String TYPE_NAME = "StockEntryInput";

    public static final String RawMaterialId = "rawMaterialId";

    public static final String Quantity = "quantity";

    public static final String Cost = "cost";

    public static final String Reason = "reason";

    public static final String Timestamp = "timestamp";

    public static final String OperationType = "operationType";

    public static final String Notes = "notes";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class RAWMATERIAL {
    public static final String TYPE_NAME = "RawMaterial";

    public static final String Id = "id";

    public static final String Name = "name";

    public static final String CategoryId = "categoryId";

    public static final String RawMaterialType = "rawMaterialType";

    public static final String Cost = "cost";

    public static final String Quantity = "quantity";
  }
}

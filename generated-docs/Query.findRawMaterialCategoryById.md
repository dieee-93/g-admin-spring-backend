# Query.findRawMaterialCategoryById: RAW_MATERIAL_CATEGORY_GQL
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| id |  | ✅ | ID! |
            
## Example
```graphql
{
  findRawMaterialCategoryById(id: "random12345") {
    id
    name
    parent
    children
    rawMaterials
  }
}

```
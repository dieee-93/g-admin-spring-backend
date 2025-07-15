# Query.findRawMaterialCategoryChildrenByParentId: [RAW_MATERIAL_CATEGORY_GQL]!
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| parent |  | ✅ | ID! |
            
## Example
```graphql
{
  findRawMaterialCategoryChildrenByParentId(parent: "random12345") {
    id
    name
    parent
    children
    rawMaterials
  }
}

```
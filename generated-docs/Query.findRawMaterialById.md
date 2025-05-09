# Query.findRawMaterialById: RAW_MATERIAL_GQL
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| id |  | ✅ | ID! |
            
## Example
```graphql
{
  findRawMaterialById(id: "random12345") {
    id
    name
    categoryId
    rawMaterialType
  }
}

```
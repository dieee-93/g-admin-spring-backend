# Query.findRawMaterialCategoryById: RawMaterialCategoryType
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| id |  | ✅ | ID! |
            
## Example
```graphql
{
  findRawMaterialCategoryById(id: "random12345") {
    id
    fatherCategoryId
    name
  }
}

```
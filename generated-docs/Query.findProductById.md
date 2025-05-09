# Query.findProductById: PRODUCT_GQL
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| id |  | ✅ | ID! |
            
## Example
```graphql
{
  findProductById(id: "random12345") {
    id
    name
    categoryId
    rawMaterialId
    cost
    quantity
    measurementUnit
    recipe
  }
}

```
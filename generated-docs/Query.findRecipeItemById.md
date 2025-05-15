# Query.findRecipeItemById: RECIPE_ITEM_GQL
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| id |  | ✅ | ID! |
            
## Example
```graphql
{
  findRecipeItemById(id: "random12345") {
    id
    product
    rawMaterial
    quantity
    cost
    measurementUnit
  }
}

```
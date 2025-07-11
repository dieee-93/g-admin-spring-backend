# Query.findProductCategoryById: PRODUCT_CATEGORY_GQL
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| id |  | ✅ | ID! |
            
## Example
```graphql
{
  findProductCategoryById(id: "random12345") {
    id
    name
    parent
    children
    products
  }
}

```
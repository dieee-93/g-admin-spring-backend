# Query.findProductCategoryChildrenByParentId: [PRODUCT_CATEGORY_GQL]!
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| parent |  | ✅ | ID! |
            
## Example
```graphql
{
  findProductCategoryChildrenByParentId(parent: "random12345") {
    id
    name
    parent
    children
    products
  }
}

```
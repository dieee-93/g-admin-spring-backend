# Query.findCategoryById: CATEGORY_GQL
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| id |  | ✅ | ID! |
            
## Example
```graphql
{
  findCategoryById(id: "random12345") {
    id
    name
    parent
    children
    products
    rawMaterials
  }
}

```
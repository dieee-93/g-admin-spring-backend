# Query.updateCategory: CATEGORY_GQL
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| id |  | ✅ | ID! |
| category |  | ✅ | CATEGORY_GQLInput! |
            
## Example
```graphql
{
  updateCategory(id: "random12345", category: {name : "randomString", parentId : "random12345"})
}

```
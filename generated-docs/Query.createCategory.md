# Query.createCategory: CATEGORY_GQL!
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| category |  | ✅ | CATEGORY_GQLInput! |
            
## Example
```graphql
{
  createCategory(category: {name : "randomString", parentId : "random12345"})
}

```
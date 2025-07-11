# Query.rolesByCompany: [RoleGQL!]!
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| companyId |  | ✅ | ID! |
            
## Example
```graphql
{
  rolesByCompany(companyId: "random12345") {
    id
    name
    description
    isSystem
    permissions
    parentRole
    createdAt
    updatedAt
    active
  }
}

```
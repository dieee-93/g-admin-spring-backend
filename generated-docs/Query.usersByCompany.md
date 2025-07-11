# Query.usersByCompany: [UserGQL!]!
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| companyId |  | ✅ | ID! |
            
## Example
```graphql
{
  usersByCompany(companyId: "random12345") {
    id
    firstName
    lastName
    email
    username
    keycloakId
    roles
    branches
    active
    createdAt
    updatedAt
  }
}

```
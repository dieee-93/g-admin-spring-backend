# Query.branchesByCompany: [BranchGQL!]!
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| companyId |  | ✅ | ID! |
            
## Example
```graphql
{
  branchesByCompany(companyId: "random12345") {
    id
    name
    code
    address
    phone
    email
    isMain
    company
    createdAt
    updatedAt
    active
  }
}

```
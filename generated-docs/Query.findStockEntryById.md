# Query.findStockEntryById: STOCK_ENTRY_GQL
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| id |  | ✅ | ID! |
            
## Example
```graphql
{
  findStockEntryById(id: "random12345") {
    id
    rawMaterial
    quantity
    cost
    recipe
    creationDate
  }
}

```
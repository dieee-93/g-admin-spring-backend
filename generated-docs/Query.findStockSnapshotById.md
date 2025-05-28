# Query.findStockSnapshotById: STOCK_SNAPSHOT_GQL
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| id |  | ✅ | ID! |
            
## Example
```graphql
{
  findStockSnapshotById(id: "random12345") {
    id
    rawMaterial
    quantity
    cost
    measurementUnit
  }
}

```
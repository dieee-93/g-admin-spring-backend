// ================================
// RecipeItemDataFetcher.java
// ================================
package diego.soro.model.recipe_item.datafetcher;

import com.netflix.graphql.dgs.*;
import diego.soro.graphql.generated.DgsConstants;
import diego.soro.graphql.generated.types.RECIPE_ITEM_GQL;
import diego.soro.model.recipe_item.dataloaders.RecipeItemByProductDataLoader;
import diego.soro.model.recipe_item.dataloaders.RecipeItemByStockEntryDataloader;
import diego.soro.model.stock.StockEntry;
import org.dataloader.DataLoader;

import java.util.concurrent.CompletableFuture;


@DgsComponent
public class RecipeItemDataFetcher {

    @DgsData(parentType = "PRODUCT_GQL", field = "recipe")
    public CompletableFuture<RECIPE_ITEM_GQL> recipe(DgsDataFetchingEnvironment dfe) {
        RECIPE_ITEM_GQL item = dfe.getSource();
        DataLoader<Long, RECIPE_ITEM_GQL> loader = dfe.getDataLoader(RecipeItemByProductDataLoader.class);
        return loader.load(Long.parseLong(item.getId()));
    }

    @DgsData(parentType = "STOCK_ENTRY_GQL", field = "recipe")
    public CompletableFuture<StockEntry> stockEntry(DgsDataFetchingEnvironment dfe) {
        RECIPE_ITEM_GQL item = dfe.getSource();
        DataLoader<Long, StockEntry> loader = dfe.getDataLoader(RecipeItemByStockEntryDataloader.class);
        return loader.load(Long.parseLong(item.getId()));
    }
}
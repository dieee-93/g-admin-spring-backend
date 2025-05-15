// ================================
// RecipeItemDataFetcher.java
// ================================
package diego.soro.model.recipe_item.datafetcher;

import com.netflix.graphql.dgs.*;
import diego.soro.graphql.generated.DgsConstants;
import diego.soro.graphql.generated.types.RECIPE_ITEM_GQL;
import diego.soro.model.product.Product;
import diego.soro.model.product.dataloader.ProductDataLoader;
import diego.soro.model.raw_material.RawMaterial;
import diego.soro.model.raw_material.dataloaders.RawMaterialDataLoader;
import org.dataloader.DataLoader;

import java.util.concurrent.CompletableFuture;


@DgsComponent
public class RecipeItemDataFetcher {

    @DgsData(parentType = DgsConstants.RECIPE_ITEM_GQL.TYPE_NAME)
    public CompletableFuture<Product> product(DgsDataFetchingEnvironment dfe) {
        RECIPE_ITEM_GQL item = dfe.getSource();
        DataLoader<Long, Product> loader = dfe.getDataLoader(ProductDataLoader.class);
        return loader.load(item);
    }

    @DgsData(parentType = DgsConstants.RECIPE_ITEM_GQL.TYPE_NAME)
    public CompletableFuture<RawMaterial> rawMaterial(DgsDataFetchingEnvironment dfe) {
        RECIPE_ITEM_GQL item = dfe.getSource();
        DataLoader<Long, RawMaterial> loader = dfe.getDataLoader(RawMaterialDataLoader.class);
        return loader.load(item.getRawMaterialId());
    }
}
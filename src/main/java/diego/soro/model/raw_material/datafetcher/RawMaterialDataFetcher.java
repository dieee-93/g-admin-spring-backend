package diego.soro.model.raw_material.datafetcher;

import com.netflix.graphql.dgs.*;

import diego.soro.model.category.Category;
import diego.soro.model.category.dataloaders.CategoryDataLoader;
import diego.soro.model.raw_material.RawMaterial;
import org.dataloader.DataLoader;

import java.util.concurrent.CompletableFuture;

@DgsComponent
public class RawMaterialDataFetcher {

    @DgsData(parentType = "RAW_MATERIAL_GQL")
    public CompletableFuture<Category> category(DgsDataFetchingEnvironment dfe) {
        RawMaterial rm = dfe.getSource();
        DataLoader<Long, Category> loader = dfe.getDataLoader(CategoryDataLoader.class);
        return loader.load(rm.getCategory().getId());
    }
}

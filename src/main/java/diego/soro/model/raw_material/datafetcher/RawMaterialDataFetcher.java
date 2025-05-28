package diego.soro.model.raw_material.datafetcher;

import com.netflix.graphql.dgs.*;

import diego.soro.graphql.generated.DgsConstants;
import diego.soro.graphql.generated.types.CREATE_RAW_MATERIAL_RESPONSE;
import diego.soro.graphql.generated.types.RAW_MATERIAL_GQLInput;
import diego.soro.model.category.Category;
import diego.soro.model.category.dataloaders.CategoryDataLoader;
import diego.soro.model.category.dataloaders.CategoryRawMaterialsDataLoader;
import diego.soro.model.category.service.CategoryService;
import diego.soro.model.raw_material.RawMaterial;
import diego.soro.model.raw_material.dataloaders.RawMaterialDataLoader;
import diego.soro.model.raw_material.mappers.RawMaterialMapper;
import diego.soro.model.raw_material.service.RawMaterialService;
import diego.soro.model.stock.StockEntry;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@DgsComponent
public class RawMaterialDataFetcher {

    private final RawMaterialService rawMaterialService;
    private final RawMaterialMapper rawMaterialMapper;
    private final CategoryService categoryService;

    @Autowired
    public RawMaterialDataFetcher(RawMaterialService rawMaterialService, RawMaterialMapper rawMaterialMapper, CategoryService categoryService) {
        this.rawMaterialService = rawMaterialService;
        this.categoryService = categoryService;
        this.rawMaterialMapper = rawMaterialMapper;
    }

    @DgsData(parentType = "CATEGORY_GQL", field = "rawMaterials")
    public CompletableFuture<List<RawMaterial>> rawMaterials(DgsDataFetchingEnvironment dfe) {
        Category category = dfe.getSource();
        DataLoader<Long, List<RawMaterial>> loader = dfe.getDataLoader(CategoryRawMaterialsDataLoader.class);
        return loader.load(category.getId());
    }

    @DgsQuery
    public RawMaterial findRawMaterialById(@InputArgument String id) throws Exception {
        return rawMaterialService.findRawMaterialById(id);
    }
    @DgsQuery
    public List<RawMaterial> findAllRawMaterials() throws Exception {
        return rawMaterialService.findRawMaterials();
    }


    @DgsMutation
    public RawMaterial createRawMaterial(@InputArgument("rawMaterial") RAW_MATERIAL_GQLInput rawMaterial) {


                Category category = categoryService.findById(rawMaterial.getCategory());
                RawMaterial toCreate = rawMaterialMapper.GQLToEntity(rawMaterial);
                toCreate.setCategory(category);
                return rawMaterialService.saveRawMaterial(toCreate);


    }
    @DgsMutation
    public Boolean removeRawMaterial(@InputArgument("id") String id) {
            rawMaterialService.removeRawMaterial(Long.parseLong(id));
            return true;
    }

        }



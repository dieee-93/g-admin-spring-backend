package diego.soro.model.raw_material.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import diego.soro.graphql.generated.types.CreateRawMaterialResponse;
import diego.soro.graphql.generated.types.RawMaterialGQL;
import diego.soro.graphql.generated.types.RawMaterialInput;
import diego.soro.model.raw_material.RawMaterial;
import diego.soro.model.raw_material.mappers.RawMaterialMapper;
import diego.soro.model.raw_material.service.RawMaterialService;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class RawMaterialDataFetcher {

    private final RawMaterialService rawMaterialService;
    private final RawMaterialMapper rawMaterialMapper;

    public RawMaterialDataFetcher(RawMaterialService rawMaterialService,
                                  RawMaterialMapper rawMaterialMapper) {
        this.rawMaterialService = rawMaterialService;
        this.rawMaterialMapper


                = rawMaterialMapper;
    }

    @DgsQuery
    public List<RawMaterial> getRawMaterials() throws IOException {
        // Obtiene las entidades y las mapea a tipos GraphQL
        return rawMaterialService.getRawMaterials().stream()
                .map(rawMaterialMapper::GQLToEntity)
                .collect(Collectors.toList());
    }

    @DgsQuery
    public RawMaterial findRawMaterialById(@InputArgument String id) throws IOException {
        // Busca la entidad y la mapea a tipo GraphQL
        var entity = rawMaterialService.findRawMaterialById(Long.parseLong(id));
        return entity != null
                ? rawMaterialMapper.toGraphql(entity)
                : null;
    }

    @DgsQuery
    public int getRawMaterialCount() throws IOException {
        return rawMaterialService.getRawMaterials().size();
    }

    @DgsMutation
    public CreateRawMaterialResponse createRawMaterial(@InputArgument("rawMaterial") RawMaterialInput input) {
        // Convierte el input GraphQL a entidad
        var entity = rawMaterialMapper.toEntity(input);
        // Guarda la entidad
        var saved = rawMaterialService.saveRawMaterial(entity);
        // Construye la respuesta
        CreateRawMaterialResponse response = new CreateRawMaterialResponse();
        response.setCode(200);
        response.setSuccess(true);
        response.setMessage("Materia prima creada exitosamente");
        // Mapea la entidad guardada a tipo GraphQL
        response.setRawMaterial(rawMaterialMapper.toGraphql(saved));
        return response;
    }

    @DgsMutation
    public RawMaterial updateRawMaterial(@InputArgument String id,
                                         @InputArgument("rawMaterial") RawMaterialInput input) {
        // Convierte el input y actualiza la entidad
        var entity = rawMaterialMapper.toEntity(input);
        var updated = rawMaterialService.updateRawMaterial(Long.parseLong(id), entity);
        // Mapea la entidad actualizada a GraphQL
        return rawMaterialMapper.toGraphql(updated);
    }

    @DgsMutation
    public Boolean removeRawMaterial(@InputArgument String id) throws IOException {
        rawMaterialService.removeRawMaterial(Long.parseLong(id));
        return true;
    }
}
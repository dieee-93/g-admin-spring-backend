package diego.soro.model.product.mappers;

import diego.soro.graphql.generated.types.ProductInput;
import diego.soro.model.product.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductInput input);
}

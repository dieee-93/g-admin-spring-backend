package diego.soro.model.product.mappers;

import diego.soro.graphql.generated.types.PRODUCT_GQL;
import diego.soro.graphql.generated.types.PRODUCT_GQLInput;
import diego.soro.model.product.Product;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product QQLtoEntity(PRODUCT_GQLInput product);
    PRODUCT_GQL entityToQGL(Product entity);
}


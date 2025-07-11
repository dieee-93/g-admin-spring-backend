package diego.soro.model2.core.Company;

import diego.soro.graphql.generated.types.CompanyGQL;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    // DTO → Entity
    Company toEntity(CreateCompanyDTO dto);

    // Entity → GraphQL Type
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "taxId", source = "taxId")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "country", source = "country")
    @Mapping(target = "timezone", source = "timezone")
    @Mapping(target = "createdAt", expression = "java(entity.getCreatedAt().toString())")
    @Mapping(target = "updatedAt", expression = "java(entity.getUpdatedAt().toString())")
    @Mapping(target = "active", source = "active")
    CompanyGQL toGraphQL(Company entity);
}
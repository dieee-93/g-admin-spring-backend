package diego.soro.model2.core.Branch;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BranchMapper {

    BranchMapper INSTANCE = Mappers.getMapper(BranchMapper.class);

    @Mapping(source = "companyId", target = "company.id") // Asumimos que se setea solo el ID, no la entidad completa
    Branch toEntity(CreateBranchDTO dto);
}
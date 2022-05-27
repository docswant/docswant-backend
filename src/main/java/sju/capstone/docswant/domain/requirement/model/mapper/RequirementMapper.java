package sju.capstone.docswant.domain.requirement.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sju.capstone.docswant.domain.requirement.model.dto.RequirementDto;
import sju.capstone.docswant.domain.requirement.model.entity.Requirement;

@Mapper
public interface RequirementMapper {

    RequirementMapper INSTANCE = Mappers.getMapper(RequirementMapper.class);

    RequirementDto.Response toDto(Requirement requirement);

    Requirement toEntity(RequirementDto.Request requestDto);
}

package sju.capstone.docswant.domain.rounding.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import sju.capstone.docswant.domain.rounding.model.dto.RoundingDto;
import sju.capstone.docswant.domain.rounding.model.entity.Rounding;

@Mapper
public interface RoundingMapper {

    RoundingMapper INSTANCE = Mappers.getMapper(RoundingMapper.class);

    @Mappings({
            @Mapping(target = "roundingSchedule.roundingDate", source = "roundingDate"),
            @Mapping(target = "roundingSchedule.roundingTime", source = "roundingTime")
    })
    Rounding toEntity(RoundingDto.Request requestDto);

    @Mappings({
            @Mapping(target = "roundingDate", source = "roundingSchedule.roundingDate"),
            @Mapping(target = "roundingTime", source = "roundingSchedule.roundingTime")
    })
    RoundingDto.Response toDto(Rounding rounding);

}

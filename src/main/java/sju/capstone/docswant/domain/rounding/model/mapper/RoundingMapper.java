package sju.capstone.docswant.domain.rounding.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import sju.capstone.docswant.domain.rounding.model.dto.RoundingDto;
import sju.capstone.docswant.domain.rounding.model.entity.Rounding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface RoundingMapper {

    RoundingMapper INSTANCE = Mappers.getMapper(RoundingMapper.class);

    Rounding toEntity(RoundingDto.Request requestDto);

    @Mappings({
            @Mapping(target = "roundingDate", source = "roundingSchedule.roundingDate"),
            @Mapping(target = "patientName", source = "patient.name"),
            @Mapping(target = "roundingTime", source = "roundingSchedule.roundingTime")
    })
    RoundingDto.Response toDto(Rounding rounding);

    default List<RoundingDto.ListResponse> toListDto(List<Rounding> roundings) {
        List<RoundingDto.ListResponse> listResponseDto = new ArrayList<>();
        MultiValueMap<Integer, RoundingDto.Response> roundingDtoMap = new LinkedMultiValueMap<>();
        roundings.forEach(rounding -> roundingDtoMap.add(rounding.getPatient().getHospitalRoom(), toDto(rounding)));
        for (Map.Entry<Integer, List<RoundingDto.Response>> entry : roundingDtoMap.entrySet()) {
            listResponseDto.add(RoundingDto.ListResponse.builder().hospitalRoom(entry.getKey()).roundings(entry.getValue()).build());
        }
        return listResponseDto;
    }

}

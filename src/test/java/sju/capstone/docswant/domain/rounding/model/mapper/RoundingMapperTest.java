package sju.capstone.docswant.domain.rounding.model.mapper;

import org.junit.jupiter.api.Test;
import sju.capstone.docswant.common.factory.DtoFactory;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.domain.rounding.model.dto.RoundingDto;
import sju.capstone.docswant.domain.rounding.model.entity.Rounding;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RoundingMapperTest {

    private RoundingMapper mapper = RoundingMapper.INSTANCE;

    @Test
    void 엔티에서_DTO_테스트() {
        //given
        Rounding requestEntity = EntityFactory.getRoundingEntity();

        //when
        RoundingDto.Response resultDto = mapper.toDto(requestEntity);

        //then
        assertThat(resultDto.getRoundingDate()).isEqualTo(requestEntity.getRoundingSchedule().getRoundingDate());
        assertThat(resultDto.getPatientName()).isEqualTo(requestEntity.getPatient().getName());
        assertThat(resultDto.getRoundingTime()).isEqualTo(requestEntity.getRoundingSchedule().getRoundingTime());
    }

    @Test
    void DTO에서_엔티티_테스트() {
        //given
        RoundingDto.Request requestDto = DtoFactory.getRoundingRequestDto();

        //when
        Rounding resultEntity = mapper.toEntity(requestDto);

        //then
        assertThat(resultEntity.getRoundingSchedule().getRoundingDate()).isEqualTo(requestDto.getRoundingDate());
        assertThat(resultEntity.getRoundingSchedule().getRoundingTime()).isEqualTo(requestDto.getRoundingTime());
    }

    @Test
    void 엔티티_리스트에서_리스트_DTO_테스트() {
        //given
        List<Rounding> roundings = EntityFactory.getRoundingEntities();

        //when
        List<RoundingDto.ListResponse> listResponseDto = mapper.toListDto(roundings);

        //then
        assertThat(listResponseDto.get(0).getHospitalRoom()).isEqualTo(roundings.get(0).getPatient().getHospitalRoom());
        assertThat(listResponseDto.get(0).getRoundings().size()).isEqualTo(roundings.size());
        assertThat(listResponseDto.get(0).getRoundings().get(0).getPatientName()).isEqualTo(roundings.get(0).getPatient().getName());
        assertThat(listResponseDto.get(0).getRoundings().get(0).getRoundingStatus()).isEqualTo(roundings.get(0).getRoundingStatus());
    }

}
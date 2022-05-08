package sju.capstone.docswant.domain.member.model.mapper;

import org.junit.jupiter.api.Test;
import sju.capstone.docswant.common.factory.DtoFactory;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.domain.member.model.dto.DoctorDto;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;

import static org.assertj.core.api.Assertions.assertThat;

class DoctorMapperTest {

    private DoctorMapper mapper = DoctorMapper.INSTANCE;

    @Test
    void 엔티티에서_DTO_테스트() {
        //given
        Doctor requestEntity = EntityFactory.getDoctorEntity();

        //when
        DoctorDto.Response resultDto = mapper.toDto(requestEntity);

        //then
        assertThat(resultDto.getCode()).isEqualTo(requestEntity.getCode());
        assertThat(resultDto.getName()).isEqualTo(requestEntity.getName());
        assertThat(resultDto.getMajor()).isEqualTo(requestEntity.getMajor());
    }

    @Test
    void DTO에서_엔티티_테스트() {
        //given
        DoctorDto.Request requestDto = DtoFactory.getDoctorRequestDto();

        //when
        Doctor resultEntity = mapper.toEntity(requestDto);

        //then
        assertThat(resultEntity.getCode()).isEqualTo(requestDto.getCode());
        assertThat(resultEntity.getUsername()).isEqualTo(requestDto.getUsername());
        assertThat(resultEntity.getPassword()).isEqualTo(requestDto.getPassword());
        assertThat(resultEntity.getName()).isEqualTo(requestDto.getName());
        assertThat(resultEntity.getMajor()).isEqualTo(requestDto.getMajor());
    }
}
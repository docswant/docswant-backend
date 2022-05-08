package sju.capstone.docswant.domain.member.model.mapper;

import org.junit.jupiter.api.Test;
import sju.capstone.docswant.common.factory.DtoFactory;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.domain.member.model.dto.PatientDto;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.member.model.entity.patient.PatientSchedule;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PatientMapperTest {

    private PatientMapper mapper = PatientMapper.INSTANCE;

    @Test
    void 엔티티에서_DTO_테스트() {
        //given
        Patient requestEntity = EntityFactory.getPatientEntity();

        //when
        PatientDto.Response resultDto = mapper.toDto(requestEntity);

        //then
        assertThat(resultDto.getCode()).isEqualTo(requestEntity.getCode());
        assertThat(resultDto.getHospitalizationDate()).isEqualTo(requestEntity.getPatientSchedule().getHospitalizationDate());
    }

    @Test
    void DTO에서_엔티티_테스트() {
        //given
        PatientDto.Request requestDto = DtoFactory.getPatientRegisterRequestDto();

        //when
        Patient resultEntity = mapper.toEntity(requestDto);

        //then
        assertThat(resultEntity.getCode()).isEqualTo(requestDto.getCode());
        assertThat(resultEntity.getPatientSchedule().getHospitalizationDate()).isEqualTo(requestDto.getHospitalizationDate());
    }
}
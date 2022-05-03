package sju.capstone.docswant.domain.member.model.mapper;

import org.junit.jupiter.api.Test;
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
        LocalDate hospitalizationDate = LocalDate.of(2022, 05, 03);
        PatientSchedule patientSchedule = PatientSchedule.builder().hospitalizationDate(hospitalizationDate).build();
        Patient requestEntity = Patient.builder().code("PATIENT001").patientSchedule(patientSchedule).build();

        //when
        PatientDto.Response resultDto = mapper.toDto(requestEntity);

        //then
        assertThat(resultDto.getCode()).isEqualTo(requestEntity.getCode());
        assertThat(resultDto.getHospitalizationDate()).isEqualTo(patientSchedule.getHospitalizationDate());
    }

    @Test
    void DTO에서_엔티티_테스트() {
        //given
        LocalDate hospitalizationDate = LocalDate.of(2022, 05, 03);
        PatientDto.Request requestDto = PatientDto.Request.builder().code("PATIENT001").hospitalizationDate(hospitalizationDate).build();

        //when
        Patient resultEntity = mapper.toEntity(requestDto);

        //then
        assertThat(resultEntity.getCode()).isEqualTo(requestDto.getCode());
        assertThat(resultEntity.getPatientSchedule().getHospitalizationDate()).isEqualTo(requestDto.getHospitalizationDate());
    }
}
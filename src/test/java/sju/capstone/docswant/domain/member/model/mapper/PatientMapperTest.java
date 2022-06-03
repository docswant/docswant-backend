package sju.capstone.docswant.domain.member.model.mapper;

import org.junit.jupiter.api.Test;
import sju.capstone.docswant.common.factory.DtoFactory;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.domain.member.model.dto.PatientDto;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.member.model.entity.patient.PatientSchedule;
import sju.capstone.docswant.domain.rounding.model.entity.Rounding;

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
        PatientDto.Request requestDto = DtoFactory.getPatientRequestDto();

        //when
        Patient resultEntity = mapper.toEntity(requestDto);

        //then
        assertThat(resultEntity.getCode()).isEqualTo(requestDto.getCode());
        assertThat(resultEntity.getPatientSchedule().getHospitalizationDate()).isEqualTo(requestDto.getHospitalizationDate());
    }

    @Test
    void 환자_회진_엔티티에서_DTO_테스트() {
        //given
        Patient patient = EntityFactory.getPatientEntity();
        Doctor doctor = EntityFactory.getDoctorEntity();
        Rounding rounding = EntityFactory.getRoundingEntity();
        patient.setDoctor(doctor);
        Integer roundsWaitingOrder = 1;

        //when
        PatientDto.PatientRoundingResponse resultDto = mapper.toPatientRoundingDto(patient, rounding, roundsWaitingOrder);

        //then
        assertThat(resultDto.getPatientName()).isEqualTo(patient.getName());
        assertThat(resultDto.getDoctorName()).isEqualTo(doctor.getName());
        assertThat(resultDto.getRoundingTime()).isEqualTo(rounding.getRoundingSchedule().getRoundingTime());
        assertThat(resultDto.getRoundsWaitingOrder()).isEqualTo(roundsWaitingOrder);
    }

    @Test
    void 엔티티에서_리스트_DTO_테스트() {
        //given
        Patient patient = EntityFactory.getPatientEntity();
        Boolean hasUnreadRequirement = false;

        //when
        PatientDto.ListResponse resultDto = mapper.toListDto(patient, hasUnreadRequirement);

        //then
        assertThat(resultDto.getCode()).isEqualTo(patient.getCode());
        assertThat(resultDto.getHospitalizationDate()).isEqualTo(patient.getPatientSchedule().getHospitalizationDate());
        assertThat(resultDto.getHasUnreadRequirement()).isEqualTo(hasUnreadRequirement);
    }
}
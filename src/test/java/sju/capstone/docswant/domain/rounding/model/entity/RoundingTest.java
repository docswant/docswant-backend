package sju.capstone.docswant.domain.rounding.model.entity;

import org.junit.jupiter.api.Test;
import sju.capstone.docswant.common.factory.DtoFactory;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.rounding.model.dto.RoundingDto;

import static org.assertj.core.api.Assertions.assertThat;

class RoundingTest {

    @Test
    void 의사_추가_테스트() {
        //given
        Rounding rounding = EntityFactory.getRoundingEntity();
        Doctor doctor = EntityFactory.getDoctorEntity();

        //when
        rounding.setDoctor(doctor);

        //then
        assertThat(rounding.getDoctor()).isEqualTo(doctor);
    }

    @Test
    void 환자_추가_테스트() {
        //given
        Rounding rounding = EntityFactory.getRoundingEntity();
        Patient patient = EntityFactory.getPatientEntity();

        //when
        rounding.setPatient(patient);

        //then
        assertThat(rounding.getPatient()).isEqualTo(patient);
    }

    @Test
    void 회진_수정_테스트() {
        //given
        Rounding rounding = EntityFactory.getRoundingEntity();
        RoundingDto.Request requestDto = DtoFactory.getRoundingUpdateRequestDto();

        //when
        rounding.update(requestDto.getRoundingDate(), requestDto.getRoundingTime());

        //then
        assertThat(rounding.getRoundingSchedule().getRoundingDate()).isEqualTo(requestDto.getRoundingDate());
        assertThat(rounding.getRoundingSchedule().getRoundingTime()).isEqualTo(requestDto.getRoundingTime());
    }

    @Test
    void 회진_상태_변경_테스트() {
        //given
        Rounding rounding = EntityFactory.getRoundingEntity();
        RoundingStatus prevStatus = rounding.getRoundingStatus();

        //when
        rounding.changeStatus();

        //then
        assertThat(rounding.getRoundingStatus()).isNotEqualTo(prevStatus);
        assertThat(rounding.getRoundingStatus()).isEqualTo(RoundingStatus.DONE);
    }
}

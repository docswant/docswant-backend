package sju.capstone.docswant.domain.member.model.entity.patient;

import org.junit.jupiter.api.Test;
import sju.capstone.docswant.common.factory.DtoFactory;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.domain.member.model.dto.PatientDto;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PatientTest {

    @Test
    void 의사_추가_테스트() {
        //given
        Patient patient = EntityFactory.getPatientEntity();
        Doctor doctor = EntityFactory.getDoctorEntity();

        //when
        patient.setDoctor(doctor);

        //then
        assertThat(patient.getDoctor()).isEqualTo(doctor);
    }

    @Test
    void 정보수정_테스트() {
        //given
        Patient patient = EntityFactory.getPatientEntity();
        PatientDto.Request requestDto = DtoFactory.getPatientUpdateRequestDto();

        //when
        patient.update(requestDto.getUsername(), requestDto.getPassword(), requestDto.getName(), requestDto.getBirthDate(),
                requestDto.getHospitalizationDate(), requestDto.getSurgeryDate(), requestDto.getDischargeDate(),
                requestDto.getDiseaseName(), requestDto.getHospitalRoom());

        //then
        assertThat(patient.getName()).isEqualTo(requestDto.getName());
        assertThat(patient.getBirthDate()).isEqualTo(requestDto.getBirthDate());
        assertThat(patient.getPatientSchedule().getDischargeDate()).isEqualTo(requestDto.getDischargeDate());
    }

}

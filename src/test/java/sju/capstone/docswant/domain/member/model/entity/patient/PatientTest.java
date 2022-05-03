package sju.capstone.docswant.domain.member.model.entity.patient;

import org.junit.jupiter.api.Test;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;

import static org.assertj.core.api.Assertions.assertThat;

class PatientTest {

    @Test
    void 의사_추가_테스트() {
        //given
        Patient patient = Patient.builder().code("PATIENT001").build();
        Doctor doctor = Doctor.builder().code("DOCTOR001").build();

        //when
        patient.setDoctor(doctor);

        //then
        assertThat(patient.getDoctor()).isEqualTo(doctor);
    }

}

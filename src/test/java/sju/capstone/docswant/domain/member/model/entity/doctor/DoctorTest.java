package sju.capstone.docswant.domain.member.model.entity.doctor;

import org.junit.jupiter.api.Test;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;

import static org.assertj.core.api.Assertions.assertThat;

class DoctorTest {

    @Test
    void 환자_추가_테스트() {
        //given
        Doctor doctor = Doctor.builder().code("DOCTOR001").build();
        Patient patient = Patient.builder().code("PATIENT001").build();

        //when
        doctor.addPatient(patient);

        //then
        assertThat(doctor.getPatients().contains(patient)).isTrue();
    }
}
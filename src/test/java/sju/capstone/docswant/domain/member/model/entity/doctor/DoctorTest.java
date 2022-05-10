package sju.capstone.docswant.domain.member.model.entity.doctor;

import org.junit.jupiter.api.Test;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;

import static org.assertj.core.api.Assertions.assertThat;

class DoctorTest {

    @Test
    void 환자_추가_테스트() {
        //given
        Doctor doctor = EntityFactory.getDoctorEntity();
        Patient patient = EntityFactory.getPatientEntity();

        //when
        doctor.addPatient(patient);

        //then
        assertThat(doctor.getPatients().contains(patient)).isTrue();
    }

    @Test
    void 정보수정_테스트() {
        //given
        Doctor doctor = EntityFactory.getDoctorEntity();
        String username = "update username";
        String password = "update password";

        //when
        doctor.update(username, password);

        //then
        assertThat(doctor.getUsername()).isEqualTo(username);
        assertThat(doctor.getPassword()).isEqualTo(password);
    }
}
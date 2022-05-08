package sju.capstone.docswant.domain.member.model.entity.patient;

import org.junit.jupiter.api.Test;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;

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

}

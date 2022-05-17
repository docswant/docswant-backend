package sju.capstone.docswant.domain.rounding.model.entity;

import org.junit.jupiter.api.Test;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;

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
}

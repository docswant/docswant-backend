package sju.capstone.docswant.domain.member.model.entity.patient;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PatientScheduleTest {

    @Test
    void 일정_수정_테스트() {
        //given
        PatientSchedule schedule = PatientSchedule.builder()
                .hospitalizationDate(LocalDate.now())
                .surgeryDate(LocalDate.now())
                .dischargeDate(LocalDate.now())
                .build();
        LocalDate updateHospitalizationDate = LocalDate.of(2022, 5, 12);
        LocalDate updateSurgeryDate = LocalDate.of(2022, 5, 13);
        LocalDate updateDischargeDate = LocalDate.of(2022, 5, 14);

        //when
        schedule.updateSchedule(updateHospitalizationDate, updateSurgeryDate, updateDischargeDate);

        //then
        assertThat(schedule.getHospitalizationDate()).isEqualTo(updateHospitalizationDate);
        assertThat(schedule.getSurgeryDate()).isEqualTo(updateSurgeryDate);
        assertThat(schedule.getDischargeDate()).isEqualTo(updateDischargeDate);
    }
}
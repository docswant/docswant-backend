package sju.capstone.docswant.domain.rounding.model.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class RoundingScheduleTest {

    @Test
    void 회진_일정_수정_테스트() {
        //given
        RoundingSchedule schedule = RoundingSchedule.builder()
                .roundingDate(LocalDate.of(2022, 5, 17))
                .roundingTime(LocalTime.of(12, 0))
                .build();
        LocalDate updateRoundingDate = LocalDate.of(2022, 5, 18);
        LocalTime updateRoundingTime = LocalTime.of(12, 30);

        //when
        schedule.updateSchedule(updateRoundingDate, updateRoundingTime);

        //then
        assertThat(schedule.getRoundingDate()).isEqualTo(updateRoundingDate);
        assertThat(schedule.getRoundingTime()).isEqualTo(updateRoundingTime);
    }
}
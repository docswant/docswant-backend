package sju.capstone.docswant.domain.rounding.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class RoundingSchedule {

    @Column(name = "rounding_date", nullable = false)
    private LocalDate roundingDate;

    @Column(name = "rounding_time", nullable = false)
    private LocalTime roundingTime;

    @Builder
    public RoundingSchedule(LocalDate roundingDate, LocalTime roundingTime) {
        this.roundingDate = roundingDate;
        this.roundingTime = roundingTime;
    }
}

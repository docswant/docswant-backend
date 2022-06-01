package sju.capstone.docswant.domain.member.model.entity.patient;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PatientSchedule {

    @Column(name = "patient_hospitalization_date", nullable = false)
    private LocalDate hospitalizationDate;

    @Column(name = "patient_surgery_date")
    private LocalDate surgeryDate;

    @Column(name = "patient_discharge_date")
    private LocalDate dischargeDate;

    @Builder
    public PatientSchedule(LocalDate hospitalizationDate, LocalDate surgeryDate, LocalDate dischargeDate) {
        this.hospitalizationDate = hospitalizationDate;
        this.surgeryDate = surgeryDate;
        this.dischargeDate = dischargeDate;
    }

    public void updateSchedule(LocalDate hospitalizationDate, LocalDate surgeryDate, LocalDate dischargeDate) {
        if (hospitalizationDate != null) {
            this.hospitalizationDate = hospitalizationDate;
        }
        if (surgeryDate != null) {
            this.surgeryDate = surgeryDate;
        }
        if (dischargeDate != null) {
            this.dischargeDate = dischargeDate;
        }
    }

}

package sju.capstone.docswant.domain.rounding.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sju.capstone.docswant.common.entity.BaseTimeEntity;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "rounding")
@Entity
public class Rounding extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private RoundingSchedule roundingSchedule;

    @Enumerated(EnumType.STRING)
    @Column(name = "rounding_status", nullable = false)
    private RoundingStatus roundingStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_code")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_code")
    private Patient patient;

    @Builder
    public Rounding(LocalDate roundingDate, LocalTime roundingTime) {
        this.roundingSchedule = RoundingSchedule.builder().roundingDate(roundingDate).roundingTime(roundingTime).build();
        this.roundingStatus = RoundingStatus.TODO;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void update(LocalDate roundingDate, LocalTime roundingTime) {
        roundingSchedule.updateSchedule(roundingDate, roundingTime);
    }

    public void changeStatus() {
        if (roundingStatus.equals(RoundingStatus.DONE)) {
            roundingStatus = RoundingStatus.TODO;
        } else {
            roundingStatus = RoundingStatus.DONE;
        }
    }
}

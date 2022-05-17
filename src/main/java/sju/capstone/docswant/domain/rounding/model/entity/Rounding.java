package sju.capstone.docswant.domain.rounding.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sju.capstone.docswant.common.entity.BaseTimeEntity;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "rounding_schedule")
@Entity
public class Rounding extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private RoundingSchedule roundingSchedule;

    @Column(name = "rounding_hospital_room", nullable = false)
    private int hospitalRoom;

    @Enumerated(EnumType.STRING)
    @Column(name = "rounding_status", nullable = false)
    private RoundingStatus roundingStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_code")
    private Doctor doctor;

    @Builder
    public Rounding(RoundingSchedule roundingSchedule, int hospitalRoom) {
        this.roundingSchedule = roundingSchedule;
        this.hospitalRoom = hospitalRoom;
        this.roundingStatus = RoundingStatus.TODO;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}

package sju.capstone.docswant.domain.member.entity.patient;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sju.capstone.docswant.domain.member.entity.Member;
import sju.capstone.docswant.domain.member.entity.Role;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("patient")
@PrimaryKeyJoinColumn(name = "patient_code")
@Table(name = "patient")
@Entity
public class Patient extends Member {

    @Column(name = "patient_name", nullable = false, length = 20)
    private String name;

    @Column(name = "patient_birth_date", nullable = false)
    private LocalDate birthDate;

    @Embedded
    private PatientSchedule patientSchedule;

    @Column(name = "patient_disease_name", nullable = false, length = 50)
    private String diseaseName;

    @Column(name = "patient_hospital_room", nullable = false)
    private int hospitalRoom;

    @Builder
    public Patient(String code, String username, String password, Role role, String name, LocalDate birthDate,
                   PatientSchedule patientSchedule, String diseaseName, int hospitalRoom) {
        super(code, username, password, role);
        this.name = name;
        this.birthDate = birthDate;
        this.patientSchedule = patientSchedule;
        this.diseaseName = diseaseName;
        this.hospitalRoom = hospitalRoom;
    }

}

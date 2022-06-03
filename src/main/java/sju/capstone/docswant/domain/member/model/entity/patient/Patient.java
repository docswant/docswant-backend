package sju.capstone.docswant.domain.member.model.entity.patient;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sju.capstone.docswant.domain.member.model.entity.Account;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.question.model.entity.Question;
import sju.capstone.docswant.domain.requirement.model.entity.Requirement;
import sju.capstone.docswant.domain.rounding.model.entity.Rounding;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("ACCOUNT_PATIENT")
@PrimaryKeyJoinColumn(name = "patient_code")
@Table(name = "patient")
@Entity
public class Patient extends Account {

    @Column(name = "patient_name", nullable = false, length = 20)
    private String name;

    @Column(name = "patient_birth_date", nullable = false)
    private LocalDate birthDate;

    @Embedded
    private PatientSchedule patientSchedule;

    @Column(name = "patient_disease_name", nullable = false, length = 50)
    private String diseaseName;

    @Column(name = "patient_hospital_room", nullable = false)
    private Integer hospitalRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_code")
    private Doctor doctor;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Rounding> roundings = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Requirement> requirements = new ArrayList<>();

    @Builder
    public Patient(String code, String username, String password, String name, LocalDate birthDate,
                   PatientSchedule patientSchedule, String diseaseName, Integer hospitalRoom) {
        super(code, username, password);
        this.name = name;
        this.birthDate = birthDate;
        this.patientSchedule = patientSchedule;
        this.diseaseName = diseaseName;
        this.hospitalRoom = hospitalRoom;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void update(String username, String password, String name, LocalDate birthDate,
                       LocalDate hospitalizationDate, LocalDate surgeryDate, LocalDate dischargeDate,
                       String diseaseName, Integer hospitalRoom) {
        updateAccount(username, password);
        patientSchedule.updateSchedule(hospitalizationDate, surgeryDate, dischargeDate);
        if (name != null) {
            this.name = name;
        }
        if (birthDate != null) {
            this.birthDate = birthDate;
        }
        if (diseaseName != null) {
            this.diseaseName = diseaseName;
        }
        if (hospitalRoom != null) {
            this.hospitalRoom = hospitalRoom;
        }
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
        question.setPatient(this);
    }

    public void addRounding(Rounding rounding) {
        this.roundings.add(rounding);
        rounding.setPatient(this);
    }

    public void addRequirement(Requirement requirement){
        this.requirements.add(requirement);
    }
}

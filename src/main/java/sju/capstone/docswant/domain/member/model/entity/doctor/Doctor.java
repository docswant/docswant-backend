package sju.capstone.docswant.domain.member.model.entity.doctor;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sju.capstone.docswant.domain.member.model.entity.Account;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.rounding.model.entity.Rounding;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("ACCOUNT_DOCTOR")
@PrimaryKeyJoinColumn(name = "doctor_code")
@Table(name = "doctor")
@Entity
public class Doctor extends Account {

    @Column(name = "doctor_name", nullable = false, length = 20)
    private String name;

    @Column(name = "doctor_major", nullable = false, length = 50)
    private String major;

    @OneToMany(mappedBy = "doctor")
    private List<Patient> patients = new ArrayList<>();

    @OneToMany(mappedBy = "doctor")
    private List<Rounding> roundings = new ArrayList<>();

    @Builder
    public Doctor(String code, String username, String password, String name, String major) {
        super(code, username, password);
        this.name = name;
        this.major = major;
    }

    public void addPatient(Patient patient) {
        this.patients.add(patient);
    }

    public void update(String username, String password) {
        updateAccount(username, password);
    }

    public void addRounding(Rounding rounding) {
        this.roundings.add(rounding);
    }
}

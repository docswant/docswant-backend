package sju.capstone.docswant.common.factory;

import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.member.model.entity.patient.PatientSchedule;

import java.time.LocalDate;

public class EntityFactory {

    public static Doctor getDoctorEntity() {
        return Doctor.builder()
                .code("DOCTOR001")
                .username("username")
                .password("password")
                .name("zooneon")
                .major("orthopedics")
                .build();
    }

    public static Patient getPatientEntity() {
        PatientSchedule patientSchedule = PatientSchedule.builder()
                .hospitalizationDate(LocalDate.of(2022, 5, 5))
                .surgeryDate(LocalDate.of(2022, 5, 8))
                .dischargeDate(LocalDate.of(2022, 5, 12))
                .build();
        return Patient.builder()
                .code("PATIENT001")
                .username("PATIENT001")
                .password("password")
                .name("zooneon")
                .birthDate(LocalDate.of(1997, 8, 26))
                .patientSchedule(patientSchedule)
                .diseaseName("COVID-19")
                .hospitalRoom(302)
                .build();
    }

}

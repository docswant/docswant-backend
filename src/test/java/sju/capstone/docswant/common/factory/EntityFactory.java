package sju.capstone.docswant.common.factory;

import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.member.model.entity.patient.PatientSchedule;
import sju.capstone.docswant.domain.question.model.entity.Question;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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

    public static List<Patient> getPatientEntities() {
        PatientSchedule patientSchedule = PatientSchedule.builder()
                .hospitalizationDate(LocalDate.of(2022, 5, 5))
                .surgeryDate(LocalDate.of(2022, 5, 8))
                .dischargeDate(LocalDate.of(2022, 5, 12))
                .build();
        return Arrays.asList(
                Patient.builder()
                        .code("PATIENT001")
                        .username("PATIENT001")
                        .password("password")
                        .name("zooneon")
                        .birthDate(LocalDate.of(1997, 8, 26))
                        .patientSchedule(patientSchedule)
                        .diseaseName("COVID-19")
                        .hospitalRoom(300)
                        .build(),
                Patient.builder()
                        .code("PATIENT002")
                        .username("PATIENT002")
                        .password("password")
                        .name("zooneon")
                        .birthDate(LocalDate.of(1997, 8, 26))
                        .patientSchedule(patientSchedule)
                        .diseaseName("COVID-19")
                        .hospitalRoom(301)
                        .build()
                ,Patient.builder()
                        .code("PATIENT003")
                        .username("PATIENT003")
                        .password("password")
                        .name("zooneon")
                        .birthDate(LocalDate.of(1997, 8, 26))
                        .patientSchedule(patientSchedule)
                        .diseaseName("COVID-19")
                        .hospitalRoom(302)
                        .build()
        );
    }

    public static Question getQuestionEntity() {
        return Question.builder()
                .id(1L)
                .content("content")
                .answer("answer")
                .build();
    }

}

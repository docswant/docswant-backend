package sju.capstone.docswant.common.factory;

import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.member.model.entity.patient.PatientSchedule;
import sju.capstone.docswant.domain.question.model.entity.Question;
import sju.capstone.docswant.domain.requirement.model.entity.Requirement;
import sju.capstone.docswant.domain.rounding.model.entity.Rounding;

import java.time.LocalDate;
import java.time.LocalTime;
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
        Patient patient = Patient.builder()
                .code("PATIENT001")
                .username("PATIENT001")
                .password("password")
                .name("zooneon")
                .birthDate(LocalDate.of(1997, 8, 26))
                .patientSchedule(patientSchedule)
                .diseaseName("COVID-19")
                .hospitalRoom(302)
                .build();
        patient.setDoctor(getDoctorEntity());
        return patient;
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
        Question question = Question.builder()
                .content("content")
                .build();
        return question;
    }

    public static List<Question> getQuestionEntities() {
        Question question1 = Question.builder()
                .content("content")
                .build();

        Question question2 = Question.builder()
                .content("content2")
                .build();
        question2.answer("answer2");

        Question question3 = Question.builder()
                .content("content3")
                .build();
        question3.answer("answer3");

        return Arrays.asList(question1, question2, question3);
    }

    public static Rounding getRoundingEntity() {
        Rounding rounding = Rounding.builder()
                .roundingDate(LocalDate.of(2022, 5, 17))
                .roundingTime(LocalTime.of(12, 0))
                .build();
        rounding.setDoctor(getDoctorEntity());
        rounding.setPatient(getPatientEntity());
        return rounding;
    }

    public static List<Rounding> getRoundingEntities() {
        Rounding rounding1 = Rounding.builder()
                .roundingDate(LocalDate.of(2022, 5, 17))
                .roundingTime(LocalTime.of(12, 0))
                .build();
        rounding1.setDoctor(getDoctorEntity());
        rounding1.setPatient(getPatientEntity());

        Rounding rounding2 = Rounding.builder()
                .roundingDate(LocalDate.of(2022, 5, 17))
                .roundingTime(LocalTime.of(12, 5))
                .build();
        rounding2.setDoctor(getDoctorEntity());
        rounding2.setPatient(getPatientEntity());

        Rounding rounding3 = Rounding.builder()
                .roundingDate(LocalDate.of(2022, 5, 17))
                .roundingTime(LocalTime.of(12, 10))
                .build();
        rounding3.setDoctor(getDoctorEntity());
        rounding3.setPatient(getPatientEntity());

        return Arrays.asList(rounding1, rounding2, rounding3);
    }

    public static Requirement getRequirementEntity() {
        return Requirement.builder()
                .title("title")
                .content("content")
                .build();
    }

    public static List<Requirement> getRequirementEntities() {
        return Arrays.asList(
                Requirement.builder()
                        .title("title1")
                        .content("content1")
                        .build(),
                Requirement.builder()
                        .title("title2")
                        .content("content2")
                        .build(),
                Requirement.builder()
                        .title("title3")
                        .content("content3")
                        .build()
        );
    }
}

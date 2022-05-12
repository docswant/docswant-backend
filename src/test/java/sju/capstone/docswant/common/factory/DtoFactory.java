package sju.capstone.docswant.common.factory;

import sju.capstone.docswant.domain.member.model.dto.AccountDto;
import sju.capstone.docswant.domain.member.model.dto.DoctorDto;
import sju.capstone.docswant.domain.member.model.dto.PatientDto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DtoFactory {

    public static AccountDto.Request getAccountRequestDto() {
        return AccountDto.Request.builder()
                .username("username")
                .password("password")
                .build();
    }

    public static AccountDto.Response getAccountResponseDto() {
        return AccountDto.Response.builder()
                .code("DOCTOR001")
                .accountType("ACCOUNT_DOCTOR")
                .accessToken("ACCESS_TOKEN")
                .refreshToken("REFRESH_TOKEN")
                .build();
    }


    public static DoctorDto.Request getDoctorRequestDto() {
        return DoctorDto.Request.builder()
                .code("DOCTOR002")
                .username("doctor")
                .password("password")
                .name("zooneon")
                .major("orthopedics")
                .build();
    }

    public static DoctorDto.Response getDoctorResponseDto() {
        return DoctorDto.Response.builder()
                .code("DOCTOR002")
                .username("doctor")
                .name("zooneon")
                .major("orthopedics")
                .build();
    }

    public static DoctorDto.Request getDoctorUpdateRequestDto() {
        return DoctorDto.Request.builder()
                .code("DOCTOR001")
                .username("update username")
                .password("update password")
                .name("zooneon")
                .major("orthopedics")
                .build();
    }

    public static DoctorDto.Response getDoctorUpdateResponseDto() {
        return DoctorDto.Response.builder()
                .code("DOCTOR001")
                .username("update username")
                .name("zooneon")
                .major("orthopedics")
                .build();
    }

    public static PatientDto.Request getPatientRequestDto() {
        return PatientDto.Request.builder()
                .code("PATIENT001")
                .name("zooneon")
                .birthDate(LocalDate.of(1997, 8, 26))
                .hospitalizationDate(LocalDate.of(2022, 5, 5))
                .diseaseName("COVID-19")
                .hospitalRoom(302)
                .build();
    }

    public static PatientDto.Response getPatientResponseDto() {
        return PatientDto.Response.builder()
                .code("PATIENT001")
                .username("PATIENT001")
                .name("zooneon")
                .birthDate(LocalDate.of(1997, 8, 26))
                .hospitalizationDate(LocalDate.of(2022, 5, 5))
                .diseaseName("COVID-19")
                .hospitalRoom(302)
                .build();
    }

    public static PatientDto.Request getPatientUpdateRequestDto() {
        return PatientDto.Request.builder()
                .code("PATIENT001")
                .username("zooneon")
                .name("zooneon")
                .birthDate(LocalDate.of(1997, 8, 26))
                .hospitalizationDate(LocalDate.of(2022, 5, 5))
                .surgeryDate(LocalDate.of(2022, 5, 8))
                .dischargeDate(LocalDate.of(2022, 5, 12))
                .diseaseName("COVID-19")
                .hospitalRoom(302)
                .build();
    }

    public static PatientDto.Response getPatientUpdateResponseDto() {
        return PatientDto.Response.builder()
                .code("PATIENT001")
                .username("PATIENT001")
                .name("zooneon")
                .birthDate(LocalDate.of(1997, 8, 26))
                .hospitalizationDate(LocalDate.of(2022, 5, 5))
                .surgeryDate(LocalDate.of(2022, 5, 8))
                .dischargeDate(LocalDate.of(2022, 5, 12))
                .diseaseName("COVID-19")
                .hospitalRoom(302)
                .build();
    }

    public static List<PatientDto.Response> getAllPatientResponseDto() {
        return Arrays.asList(
                PatientDto.Response.builder()
                        .code("PATIENT001")
                        .username("PATIENT001")
                        .name("zooneon")
                        .birthDate(LocalDate.of(1997, 8, 26))
                        .hospitalizationDate(LocalDate.of(2022, 5, 5))
                        .diseaseName("COVID-19")
                        .hospitalRoom(300)
                        .build(),
                PatientDto.Response.builder()
                        .code("PATIENT002")
                        .username("PATIENT002")
                        .name("zooneon")
                        .birthDate(LocalDate.of(1997, 8, 26))
                        .hospitalizationDate(LocalDate.of(2022, 5, 5))
                        .diseaseName("COVID-19")
                        .hospitalRoom(301)
                        .build(),
                PatientDto.Response.builder()
                        .code("PATIENT003")
                        .username("PATIENT003")
                        .name("zooneon")
                        .birthDate(LocalDate.of(1997, 8, 26))
                        .hospitalizationDate(LocalDate.of(2022, 5, 5))
                        .diseaseName("COVID-19")
                        .hospitalRoom(302)
                        .build()
        );
    }

}

package sju.capstone.docswant.domain.member.model.dto.doctor;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DoctorDTO {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Request {
        @NotNull
        private String code;
        @NotBlank
        private String username;
        @NotBlank
        private String password;
        @NotBlank
        private String name;
        @NotBlank
        private String major;

        @Builder
        public Request(String code, String username, String password, String name, String major) {
            this.code = code;
            this.username = username;
            this.password = password;
            this.name = name;
            this.major = major;
        }

        public Doctor toEntity() {
            return Doctor.builder()
                    .code(code)
                    .username(username)
                    .password(password)
                    .name(name)
                    .major(major)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private String code;


        @Builder
        public Response(String code, String accessToken, String refreshToken) {
            this.code = code;

        }
    }

}

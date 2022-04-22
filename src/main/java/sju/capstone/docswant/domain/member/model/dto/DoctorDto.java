package sju.capstone.docswant.domain.member.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DoctorDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Request {
        @NotBlank @Size(max = 30)
        private String code;
        @NotBlank @Size(max = 30)
        private String username;
        @NotBlank @Size(max = 50)
        private String password;
        @NotBlank @Size(max = 20)
        private String name;
        @NotBlank @Size(max = 50)
        private String major;

        @Builder
        public Request(String code, String username, String password, String name, String major) {
            this.code = code;
            this.username = username;
            this.password = password;
            this.name = name;
            this.major = major;
        }

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private String code;
        private String name;
        private String major;

        @Builder
        public Response(String code, String name, String major) {
            this.code = code;
            this.name = name;
            this.major = major;
        }

    }

}

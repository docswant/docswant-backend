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
        @Size(max = 30)
        @NotBlank(message = "필수 값입니다.")
        private String code;
        @Size(max = 30)
        @NotBlank(message = "필수 값입니다.")
        private String username;
        @Size(max = 50)
        @NotBlank(message = "필수 값입니다.")
        private String password;
        @Size(max = 20)
        @NotBlank(message = "필수 값입니다.")
        private String name;
        @Size(max = 50)
        @NotBlank(message = "필수 값입니다.")
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

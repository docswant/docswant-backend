package sju.capstone.docswant.domain.member.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatientDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        @Size(max = 30)
        @NotBlank(message = "필수 값입니다.")
        private String code;

        @Size(max = 30)
        private String username;

        @Size(max = 50)
        private String password;

        @Size(max = 20)
        @NotBlank(message = "필수 값입니다.")
        private String name;

        @NotNull(message = "필수 값입니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate birthDate;

        @NotNull(message = "필수 값입니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate hospitalizationDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate surgeryDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate dischargeDate;

        @Size(max = 50)
        @NotBlank(message = "필수 값입니다.")
        private String diseaseName;

        @NotNull(message = "필수 값입니다.")
        private int hospitalRoom;

        @Builder
        public Request(String code, String username, String password, String name, LocalDate birthDate, LocalDate hospitalizationDate,
                       LocalDate surgeryDate, LocalDate dischargeDate, String diseaseName, int hospitalRoom) {
            this.code = code;
            this.username = username;
            this.password = password;
            this.name = name;
            this.birthDate = birthDate;
            this.hospitalizationDate = hospitalizationDate;
            this.surgeryDate = surgeryDate;
            this.dischargeDate = dischargeDate;
            this.diseaseName = diseaseName;
            this.hospitalRoom = hospitalRoom;
        }
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {
        private String code;
        private String username;
        private String name;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate birthDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate hospitalizationDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate surgeryDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate dischargeDate;

        private String diseaseName;
        private int hospitalRoom;

        @Builder
        public Response(String code, String username, String name, LocalDate birthDate, LocalDate hospitalizationDate,
                        LocalDate surgeryDate, LocalDate dischargeDate, String diseaseName, int hospitalRoom) {
            this.code = code;
            this.username = username;
            this.name = name;
            this.birthDate = birthDate;
            this.hospitalizationDate = hospitalizationDate;
            this.surgeryDate = surgeryDate;
            this.dischargeDate = dischargeDate;
            this.diseaseName = diseaseName;
            this.hospitalRoom = hospitalRoom;
        }
    }

}

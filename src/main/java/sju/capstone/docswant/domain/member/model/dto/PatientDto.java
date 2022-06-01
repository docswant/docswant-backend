package sju.capstone.docswant.domain.member.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sju.capstone.docswant.domain.rounding.model.entity.RoundingStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatientDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Request {
        @Size(max = 30)
        @NotBlank(message = "필수 값입니다.")
        private String code;

        @Size(max = 20)
        @NotBlank(message = "필수 값입니다.")
        private String name;

        @NotNull(message = "필수 값입니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate birthDate;

        @NotNull(message = "필수 값입니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate hospitalizationDate;

        @Size(max = 50)
        @NotBlank(message = "필수 값입니다.")
        private String diseaseName;

        @NotNull(message = "필수 값입니다.")
        private Integer hospitalRoom;

        @Builder
        public Request(String code, String name, LocalDate birthDate, LocalDate hospitalizationDate, String diseaseName, Integer hospitalRoom) {
            this.code = code;
            this.name = name;
            this.birthDate = birthDate;
            this.hospitalizationDate = hospitalizationDate;
            this.diseaseName = diseaseName;
            this.hospitalRoom = hospitalRoom;
        }
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {
        private String code;
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
        private Integer hospitalRoom;

        @Builder
        public Response(String code, String name, LocalDate birthDate, LocalDate hospitalizationDate,
                        LocalDate surgeryDate, LocalDate dischargeDate, String diseaseName, Integer hospitalRoom) {
            this.code = code;
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
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UpdateRequest {
        @Size(max = 30)
        private String username;

        @Size(max = 50)
        private String password;

        @Size(max = 20)
        private String name;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate birthDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate hospitalizationDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate surgeryDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate dischargeDate;

        @Size(max = 50)
        private String diseaseName;

        private Integer hospitalRoom;

        @Builder
        public UpdateRequest(String username, String password, String name, LocalDate birthDate, LocalDate hospitalizationDate,
                             LocalDate surgeryDate, LocalDate dischargeDate, String diseaseName, Integer hospitalRoom) {
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
    public static class PatientRoundingResponse {
        private String code;
        private String patientName;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate birthDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate hospitalizationDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate surgeryDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate dischargeDate;

        private String diseaseName;
        private Integer hospitalRoom;
        private String doctorName;
        private String doctorMajor;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        private LocalTime roundingTime;

        private Integer roundsWaitingOrder;

        @Builder
        public PatientRoundingResponse(String code, String patientName, LocalDate birthDate, LocalDate hospitalizationDate,
                            LocalDate surgeryDate, LocalDate dischargeDate, String diseaseName, Integer hospitalRoom,
                            String doctorName, String doctorMajor, LocalTime roundingTime, Integer roundsWaitingOrder) {
            this.code = code;
            this.patientName = patientName;
            this.birthDate = birthDate;
            this.hospitalizationDate = hospitalizationDate;
            this.surgeryDate = surgeryDate;
            this.dischargeDate = dischargeDate;
            this.diseaseName = diseaseName;
            this.hospitalRoom = hospitalRoom;
            this.doctorName = doctorName;
            this.doctorMajor = doctorMajor;
            this.roundingTime = roundingTime;
            this.roundsWaitingOrder = roundsWaitingOrder;
        }
    }
}

package sju.capstone.docswant.domain.rounding.model.dto;

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
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoundingDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Request {
        @Size(max = 30)
        @NotBlank(message = "필수 값입니다.")
        private String code;

        @NotNull(message = "필수 값입니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate roundingDate;

        @NotNull(message = "필수 값입니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        private LocalTime roundingTime;

        @Builder
        public Request(String code, LocalDate roundingDate, LocalTime roundingTime) {
            this.code = code;
            this.roundingDate = roundingDate;
            this.roundingTime = roundingTime;
        }
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {
        private Long id;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate roundingDate;

        private String patientName;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        private LocalTime roundingTime;

        private RoundingStatus roundingStatus;

        @Builder
        public Response(Long id, LocalDate roundingDate, String patientName, LocalTime roundingTime, RoundingStatus roundingStatus) {
            this.id = id;
            this.roundingDate = roundingDate;
            this.patientName = patientName;
            this.roundingTime = roundingTime;
            this.roundingStatus = roundingStatus;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UpdateRequest {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate roundingDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        private LocalTime roundingTime;

        @Builder
        public UpdateRequest(LocalDate roundingDate, LocalTime roundingTime) {
            this.roundingDate = roundingDate;
            this.roundingTime = roundingTime;
        }
    }

    @Getter
    public static class ListResponse {
        private int hospitalRoom;
        private List<RoundingDto.Response> roundings;

        @Builder
        public ListResponse(int hospitalRoom, List<Response> roundings) {
            this.hospitalRoom = hospitalRoom;
            this.roundings = roundings;
        }
    }

}

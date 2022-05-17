package sju.capstone.docswant.domain.rounding.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sju.capstone.docswant.domain.rounding.model.entity.RoundingStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoundingDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        @NotNull(message = "필수 값입니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate roundingDate;

        @NotNull(message = "필수 값입니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        private LocalTime roundingTime;

        @NotNull(message = "필수 값입니다.")
        private int hospitalRoom;

        @Builder
        public Request(LocalDate roundingDate, LocalTime roundingTime, int hospitalRoom) {
            this.roundingDate = roundingDate;
            this.roundingTime = roundingTime;
            this.hospitalRoom = hospitalRoom;
        }
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {
        private Long id;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate roundingDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        private LocalTime roundingTime;

        private int hospitalRoom;
        private RoundingStatus roundingStatus;

        @Builder
        public Response(Long id, LocalDate roundingDate, LocalTime roundingTime,
                        int hospitalRoom, RoundingStatus roundingStatus) {
            this.id = id;
            this.roundingDate = roundingDate;
            this.roundingTime = roundingTime;
            this.hospitalRoom = hospitalRoom;
            this.roundingStatus = roundingStatus;
        }
    }

}

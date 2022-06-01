package sju.capstone.docswant.infra.esl;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.rounding.model.entity.Rounding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EslDto {

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class AuthRequest {
        private String userId;
        private String userPw;

        @Builder
        public AuthRequest(String userId, String userPw) {
            this.userId = userId;
            this.userPw = userPw;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AuthResponse {
        private String token;

        @Builder
        public AuthResponse(String token) {
            this.token = token;
        }
    }

    @Getter
    public static class ChangeRequest {

        private static final String UNREGISTERED_MESSAGE = "미등록";
        private static final String D_MINUS_PREFIX = "D-";
        private static final String D_PLUS_PREFIX = "D+";

        private String key;
        private ChangeContent change;

        @Builder
        public ChangeRequest(String key, ChangeContent change) {
            this.key = key;
            this.change = change;
        }

        public static ChangeRequest toDto(Patient patient, Rounding rounding, Integer roundsWaitingOrder) {
            ChangeContent changeContent = ChangeContent.builder()
                    .tagId(patient.getCode())
                    .patientName(patient.getName())
                    .doctorName(patient.getDoctor().getName())
                    .surgeryDDay(getDDayMessage(patient.getPatientSchedule().getSurgeryDate()))
                    .dischargeDDay(getDDayMessage(patient.getPatientSchedule().getDischargeDate()))
                    .roundingTime(getRoundingTimeMessage(rounding))
                    .roundsWaitingOrder(getRoundsWaitingOrderMessage(roundsWaitingOrder))
                    .build();
            return ChangeRequest.builder().key(patient.getCode()).change(changeContent).build();
        }

        private static String getDDayMessage(LocalDate targetDate) {
            if (targetDate == null) {
                return UNREGISTERED_MESSAGE;
            }
            StringBuilder sb = new StringBuilder();
            if (targetDate.isAfter(LocalDate.now())) {
                sb.append(D_MINUS_PREFIX).append(LocalDate.now().datesUntil(targetDate).count());
            } else {
                sb.append(D_PLUS_PREFIX).append(targetDate.datesUntil(LocalDate.now()).count());
            }
            return sb.toString();
        }

        private static String getRoundingTimeMessage(Rounding rounding) {
            if (rounding == null) {
                return UNREGISTERED_MESSAGE;
            }
            return rounding.getRoundingSchedule().getRoundingTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        }

        private static String getRoundsWaitingOrderMessage(Integer roundsWaitingOrder) {
            if (roundsWaitingOrder == null) {
                return UNREGISTERED_MESSAGE;
            }
            return String.valueOf(roundsWaitingOrder);
        }
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ChangeContent {
        private String tagId;
        private String patientName;
        private String doctorName;
        private String surgeryDDay;
        private String dischargeDDay;
        private String roundingTime;
        private String roundsWaitingOrder;

        @Builder
        public ChangeContent(String tagId, String patientName, String doctorName, String surgeryDDay, String dischargeDDay,
                             String roundingTime, String roundsWaitingOrder) {
            this.tagId = tagId;
            this.patientName = patientName;
            this.doctorName = doctorName;
            this.surgeryDDay = surgeryDDay;
            this.dischargeDDay = dischargeDDay;
            this.roundingTime = roundingTime;
            this.roundsWaitingOrder = roundsWaitingOrder;
        }
    }
}

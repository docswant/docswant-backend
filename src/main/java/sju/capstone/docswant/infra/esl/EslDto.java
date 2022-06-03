package sju.capstone.docswant.infra.esl;

import lombok.Builder;
import lombok.Getter;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.rounding.model.entity.Rounding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EslDto {

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
    public static class ChangeContent {
        private String tag_id;
        private String patient_name;
        private String doctor_name;
        private String surgery_d_day;
        private String discharge_d_day;
        private String rounding_time;
        private String rounds_waiting_order;

        @Builder
        public ChangeContent(String tagId, String patientName, String doctorName, String surgeryDDay, String dischargeDDay,
                             String roundingTime, String roundsWaitingOrder) {
            this.tag_id = tagId;
            this.patient_name = patientName;
            this.doctor_name = doctorName;
            this.surgery_d_day = surgeryDDay;
            this.discharge_d_day = dischargeDDay;
            this.rounding_time = roundingTime;
            this.rounds_waiting_order = roundsWaitingOrder;
        }
    }
}

package sju.capstone.docswant.domain.question.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sju.capstone.docswant.domain.question.model.entity.AnswerStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Request {
        @Size(max = 255)
        @NotBlank(message = "필수 값입니다.")
        private String content;

        @Builder
        public Request(String content) {
            this.content = content;
        }
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {
        private Long id;
        private String content;
        private String answer;
        private AnswerStatus answerStatus;

        @Builder
        public Response(Long id, String content, String answer, AnswerStatus answerStatus) {
            this.id = id;
            this.content = content;
            this.answer = answer;
            this.answerStatus = answerStatus;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateRequest {
        @Size(max = 255)
        @NotBlank(message = "필수 값입니다.")
        private String content;

        @Builder
        public UpdateRequest(String content) {
            this.content = content;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AnswerRequest {
        @Size(max = 100)
        @NotBlank(message = "필수 값입니다.")
        private String answer;

        @Builder
        public AnswerRequest(String answer) {
            this.answer = answer;
        }
    }
}

package sju.capstone.docswant.domain.question.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Request {
        @Size(max = 255)
        @NotBlank(message = "필수 값입니다.")
        private String content;

        @Size(max = 100)
        private String answer;

        @Builder
        public Request(String content, String answer) {
            this.content = content;
            this.answer = answer;
        }
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {
        private Long id;
        private String content;
        private String answer;

        @Builder
        public Response(Long id, String content, String answer) {
            this.id = id;
            this.content = content;
            this.answer = answer;
        }
    }

}

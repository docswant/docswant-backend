package sju.capstone.docswant.domain.requirement.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sju.capstone.docswant.domain.requirement.model.entity.RequirementStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequirementDto {

        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class Request {
            @Size(max = 500)
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
            private RequirementStatus status;

            @Builder
            public Response(Long id, String content, RequirementStatus status) {
                this.id = id;
                this.content = content;
                this.status = status;
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
}

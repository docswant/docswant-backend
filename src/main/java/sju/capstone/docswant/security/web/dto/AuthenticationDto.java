package sju.capstone.docswant.security.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthenticationDto {

    @Getter
    public static class Request {
        private String username;
        private String password;

        @JsonCreator
        public Request(
                @JsonProperty("username") String username,
                @JsonProperty("password") String password) {
            this.username = username;
            this.password = password;
        }
    }

    @Getter
    public static class Response {
        private String code;
        private String memberType;

        @Builder
        public Response(String code, String memberType) {
            this.code = code;
            this.memberType = memberType;
        }
    }

}

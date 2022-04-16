package sju.capstone.docswant.security.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountDto {

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
        private String accountType;
        private String accessToken;
        private String refreshToken;

        @Builder
        public Response(String code, String accountType, String accessToken, String refreshToken) {
            this.code = code;
            this.accountType = accountType;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }

}

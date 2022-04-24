package sju.capstone.docswant.domain.member.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
public class AccountDto {

    @Getter
    public static class Request {
        private String username;
        private String password;

        @Jacksonized
        @Builder
        public Request(String username, String password) {
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

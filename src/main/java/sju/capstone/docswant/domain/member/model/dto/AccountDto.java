package sju.capstone.docswant.domain.member.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Request {
        private String username;
        private String password;

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

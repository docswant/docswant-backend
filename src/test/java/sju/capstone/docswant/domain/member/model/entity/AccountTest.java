package sju.capstone.docswant.domain.member.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import sju.capstone.docswant.common.factory.EntityFactory;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = EntityFactory.getDoctorEntity();
    }

    @Test
    void Refresh_Token_저장_테스트() {
        //given
        String refreshToken = "refreshToken";

        //when
        account.setRefreshToken(refreshToken);

        //then
        assertThat(account.getRefreshToken()).isEqualTo(refreshToken);
    }

    @Test
    void 계정_업데이트_테스트() {
        //given
        String username = "username";
        String password = "password";

        //when
        account.updateAccount(username, password);

        //then
        assertThat(account.getUsername()).isEqualTo(username);
        assertThat(account.getPassword()).isEqualTo(password);
    }
}
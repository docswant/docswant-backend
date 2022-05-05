package sju.capstone.docswant.domain.member.model.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.security.utils.PasswordEncoderFactory;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private Account account;
    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @BeforeEach
    void setUp() {
        account = Doctor.builder().code("DOCTOR001").username("username").password("password").build();
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
        String encodedPassword = passwordEncoder.encode(account.getPassword());

        //when
        account.updateAccount(username, encodedPassword);

        //then
        assertThat(account.getUsername()).isEqualTo(username);
        assertThat(account.getPassword()).isEqualTo(encodedPassword);
    }
}
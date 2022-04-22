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
    void setRefreshToken() {
        //given
        String refreshToken = "refreshToken";

        //when
        account.setRefreshToken(refreshToken);

        //then
        assertThat(account.getRefreshToken()).isEqualTo(refreshToken);
    }

    @Test
    void setEncodedPassword() {
        //given
        String encodedPassword = passwordEncoder.encode(account.getPassword());

        //when
        account.setEncodedPassword(encodedPassword);

        //then
        assertThat(account.getPassword()).isEqualTo(encodedPassword);
    }
}
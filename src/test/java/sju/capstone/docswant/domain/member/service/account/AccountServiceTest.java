package sju.capstone.docswant.domain.member.service.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.domain.member.model.entity.Account;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.member.repository.account.AccountRepository;
import sju.capstone.docswant.security.authentication.token.JwtToken;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private JwtToken jwtToken;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void 사용자명_중복_확인_테스트() {
        //given
        String username = "username";
        given(accountRepository.existsByUsername(any(String.class))).willReturn(true);

        //when
        boolean isExists = accountService.isExistsUsername(username);

        //then
        assertThat(isExists).isTrue();
    }

    @Test
    void 토큰_재발급_테스트() {
        //given
        Account account = EntityFactory.getDoctorEntity();
        String accessToken = "access_token";
        String refreshToken = "refresh_token";
        account.setRefreshToken(refreshToken);
        given(accountRepository.findByCode(any(String.class))).willReturn(Optional.of(account));
        given(jwtToken.isValid(refreshToken)).willReturn(true);
        given(jwtToken.createAccessToken(any(Account.class))).willReturn(accessToken);

        //when
        String reissuedToken = accountService.reissueAccessToken("code", refreshToken);

        //then
        assertThat(reissuedToken).isEqualTo(accessToken);
    }
}
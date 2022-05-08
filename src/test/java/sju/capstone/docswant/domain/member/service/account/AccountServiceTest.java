package sju.capstone.docswant.domain.member.service.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sju.capstone.docswant.domain.member.repository.account.AccountRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

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
}
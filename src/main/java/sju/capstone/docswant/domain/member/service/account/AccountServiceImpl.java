package sju.capstone.docswant.domain.member.service.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sju.capstone.docswant.domain.member.model.dto.AccountDto;
import sju.capstone.docswant.domain.member.repository.account.AccountRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public boolean isExistsUsername(String username) {
        log.info("username duplication check. username = {}", username);
        return accountRepository.existsByUsername(username);
    }

    @Override
    public String reissueAccessToken(AccountDto.Request requestDto) {
        return null;
    }
}

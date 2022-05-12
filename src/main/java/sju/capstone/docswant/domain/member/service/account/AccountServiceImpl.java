package sju.capstone.docswant.domain.member.service.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sju.capstone.docswant.core.error.ErrorCode;
import sju.capstone.docswant.core.error.exception.EntityNotFoundException;
import sju.capstone.docswant.core.error.exception.InvalidValueException;
import sju.capstone.docswant.domain.member.model.dto.AccountDto;
import sju.capstone.docswant.domain.member.model.entity.Account;
import sju.capstone.docswant.domain.member.repository.account.AccountRepository;
import sju.capstone.docswant.security.authentication.token.JwtToken;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final JwtToken jwtToken;

    @Transactional(readOnly = true)
    @Override
    public boolean isExistsUsername(String username) {
        log.info("username duplication check. username = {}", username);
        return accountRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public String reissueAccessToken(String code, String refreshToken) {
        Account account = accountRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        if (account.getRefreshToken().equals(refreshToken) && jwtToken.isValid(refreshToken)) {
            String accessToken = jwtToken.createAccessToken(account);
            log.info("reissue success. code = {}", account.getCode());
            return accessToken;
        } else {
            throw new InvalidValueException(ErrorCode.INVALID_TOKEN);
        }
    }
}

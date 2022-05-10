package sju.capstone.docswant.domain.member.service.account;

import sju.capstone.docswant.domain.member.model.dto.AccountDto;

public interface AccountService {

    boolean isExistsUsername(String username);

    String reissueAccessToken(String code, String refreshToken);

}

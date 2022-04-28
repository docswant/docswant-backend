package sju.capstone.docswant.security.web.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import sju.capstone.docswant.common.format.ResponseFormat;
import sju.capstone.docswant.domain.member.model.entity.Account;
import sju.capstone.docswant.domain.member.model.mapper.AccountMapper;
import sju.capstone.docswant.domain.member.repository.account.AccountRepository;
import sju.capstone.docswant.security.authentication.token.JwtToken;
import sju.capstone.docswant.domain.member.model.dto.AccountDto;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtToken jwtToken;
    private final AccountRepository accountRepository;
    private final ObjectMapper objectMapper;
    private final AccountMapper mapper = AccountMapper.INSTANCE;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Account account = (Account) authentication.getPrincipal();
        log.info("Authentication Success. account_code = {}", account.getCode());

        String accessToken = jwtToken.createAccessToken(account);
        String refreshToken = jwtToken.createRefreshToken(account);
        account.setRefreshToken(refreshToken);
        accountRepository.save(account);

        AccountDto.Response responseDto = mapper.toDto(account, accessToken);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        objectMapper.writeValue(response.getWriter(), ResponseFormat.of(responseDto));
    }

}

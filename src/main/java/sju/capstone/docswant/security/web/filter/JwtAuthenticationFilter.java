package sju.capstone.docswant.security.web.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import sju.capstone.docswant.domain.member.model.entity.Account;
import sju.capstone.docswant.security.authentication.provider.JwtAuthenticationProvider;
import sju.capstone.docswant.security.authentication.token.JwtToken;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends GenericFilterBean {

    private static final String TOKEN_NOT_FOUND_MESSAGE = "No Token to Authenticate";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtToken jwtToken;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = resolveToken(httpRequest);

        if (!StringUtils.hasText(token)) {
            log.error("No Token to Authenticate. request url = {}", httpRequest.getRequestURL());
            throw new AuthenticationException(TOKEN_NOT_FOUND_MESSAGE);
        }

        if (jwtToken.isValid(token)) {
            Authentication authentication = jwtAuthenticationProvider.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Account account = (Account) authentication.getPrincipal();
            log.info("Authentication Success. account_code = {}", account.getCode());
        }

        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest httpRequest) {
        String bearerToken = httpRequest.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }

        return null;
    }
}

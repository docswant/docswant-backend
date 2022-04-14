package sju.capstone.docswant.security.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;
import sju.capstone.docswant.security.authentication.token.CustomAuthenticationToken;
import sju.capstone.docswant.security.web.dto.AuthenticationDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private static final String METHOD_NOT_SUPPORTED_MESSAGE = "Authentication method not supported";
    private static final String INSUFFICIENT_INPUT_VALUE_MESSAGE = "Username or Password not Provided";
    private static final String LOGIN_URL = "/api/v1/login";

    @Autowired
    private ObjectMapper objectMapper;

    public CustomAuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher(LOGIN_URL, HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        if (!HttpMethod.POST.matches(request.getMethod())) {
            log.error("Authentication method not supported. Request method = {}", request.getMethod());
            throw new AuthenticationServiceException(METHOD_NOT_SUPPORTED_MESSAGE);
        }

        AuthenticationDto.Request requestDto = objectMapper.readerFor(AuthenticationDto.Request.class).readValue(request.getReader());
        if (!StringUtils.hasText(requestDto.getUsername()) || !StringUtils.hasText(requestDto.getPassword())) {
            log.error("Username or Password not Provided. username = {} password = {}", requestDto.getUsername(), requestDto.getPassword());
            throw new AuthenticationServiceException(INSUFFICIENT_INPUT_VALUE_MESSAGE);
        }

        CustomAuthenticationToken authenticationToken = new CustomAuthenticationToken(requestDto.getUsername(), requestDto.getPassword());

        return getAuthenticationManager().authenticate(authenticationToken);
    }

}

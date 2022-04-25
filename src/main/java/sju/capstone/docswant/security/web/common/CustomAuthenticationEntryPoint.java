package sju.capstone.docswant.security.web.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import sju.capstone.docswant.common.format.ResponseFormat;
import sju.capstone.docswant.common.message.StatusMessage;
import sju.capstone.docswant.core.error.ErrorCode;
import sju.capstone.docswant.common.format.ErrorFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String UNAUTHORIZED = "Unauthorized";

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Unauthorized. message = {}, request url = {}", authException.getMessage(), request.getRequestURL());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        ErrorFormat error = ErrorFormat.of(ErrorCode.HANDLE_AUTHENTICATION_FAILED, UNAUTHORIZED);
        objectMapper.writeValue(response.getWriter(), ResponseFormat.of(StatusMessage.ERROR, error));
    }

}

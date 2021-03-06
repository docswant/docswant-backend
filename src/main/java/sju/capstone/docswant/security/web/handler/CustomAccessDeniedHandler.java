package sju.capstone.docswant.security.web.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("Access Denied. message = {}, request url = {}", accessDeniedException.getMessage(), request.getRequestURL());

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        ErrorFormat error = ErrorFormat.of(ErrorCode.HANDLE_ACCESS_DENIED);
        objectMapper.writeValue(response.getWriter(), ResponseFormat.of(StatusMessage.ERROR, error));
    }

}

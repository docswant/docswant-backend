package sju.capstone.docswant.security.web.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import sju.capstone.docswant.domain.member.entity.Member;
import sju.capstone.docswant.domain.member.repository.MemberRepository;
import sju.capstone.docswant.security.authentication.token.JwtToken;
import sju.capstone.docswant.security.web.dto.AuthenticationDto;

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
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Member member = (Member) authentication.getPrincipal();
        log.info("Authentication Success. member_code = {}", member.getCode());

        String accessToken = jwtToken.createAccessToken(member);
        String refreshToken = jwtToken.createRefreshToken(member);
        member.setRefreshToken(refreshToken);
        memberRepository.save(member);

        AuthenticationDto.Response responseDto = AuthenticationDto.Response.builder()
                .code(member.getCode()).memberType(member.getMemberType())
                .accessToken(accessToken).refreshToken(refreshToken)
                .build();
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        objectMapper.writeValue(response.getWriter(), responseDto);
    }

}

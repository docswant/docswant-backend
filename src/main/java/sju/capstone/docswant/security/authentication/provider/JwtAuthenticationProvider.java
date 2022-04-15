package sju.capstone.docswant.security.authentication.provider;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import sju.capstone.docswant.security.authentication.token.JwtToken;
import sju.capstone.docswant.security.core.userdetails.CustomUserDetailsService;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final JwtToken jwtToken;
    private final CustomUserDetailsService userDetailsService;

    private String jwt
    private JwtParser jwtParser = Jwts.parserBuilder().setSigningKey().build();

    public Authentication authenticate(String token) {

        return null;
    }
}

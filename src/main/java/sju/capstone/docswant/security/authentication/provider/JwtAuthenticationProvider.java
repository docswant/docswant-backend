package sju.capstone.docswant.security.authentication.provider;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import sju.capstone.docswant.security.authentication.token.CustomAuthenticationToken;
import sju.capstone.docswant.security.core.userdetails.CustomUserDetails;
import sju.capstone.docswant.security.core.userdetails.CustomUserDetailsService;

import javax.crypto.SecretKey;

@Slf4j
@Component
public class JwtAuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;
    private final SecretKey secretKey;
    private final JwtParser jwtParser;

    public JwtAuthenticationProvider(CustomUserDetailsService userDetailsService, @Value("${jwt.secret}") String secret) {
        this.userDetailsService = userDetailsService;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
    }

    public Authentication authenticate(String token) {
        String username = jwtParser.parseClaimsJws(token).getBody().getSubject();
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

        return new CustomAuthenticationToken(userDetails.getMember(), null, userDetails.getAuthorities());
    }
}

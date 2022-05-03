package sju.capstone.docswant.security.authentication.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sju.capstone.docswant.domain.member.model.entity.Account;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtToken {

    private static final String TOKEN_TYPE = "token type";
    private static final String REFRESH_TOKEN = "refresh token";
    private static final String ACCESS_TOKEN = "access token";

    private final SecretKey secretKey;
    private final long accessTokenExpiredTimeMillis;
    private final long refreshTokenExpiredTimeMillis;

    public JwtToken(@Value("${jwt.secret}") String secret,
                    @Value("${jwt.access-token-expired-time}") long accessTokenExpiredTime,
                    @Value("${jwt.refresh-token-expired-time}") long refreshTokenExpiredTime) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessTokenExpiredTimeMillis = accessTokenExpiredTime * 1000;
        this.refreshTokenExpiredTimeMillis = refreshTokenExpiredTime * 1000;
    }

    private String createToken(Account account, String tokenType) {
        Claims claims = Jwts.claims();
        claims.put(TOKEN_TYPE, tokenType);
        claims.setSubject(account.getUsername());
        long expiredTimeMillis = tokenType == ACCESS_TOKEN ? accessTokenExpiredTimeMillis : refreshTokenExpiredTimeMillis;
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMillis))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createAccessToken(Account account) {
        return createToken(account, ACCESS_TOKEN);
    }

    public String createRefreshToken(Account account) {
        return createToken(account, REFRESH_TOKEN);
    }

    public boolean isValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.warn("Malformed Token. message = {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.warn("Token Expired. message = {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("Unsupported Token. message = {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("Token Not Found. message = {}", e.getMessage());
        }

        return false;
    }
}

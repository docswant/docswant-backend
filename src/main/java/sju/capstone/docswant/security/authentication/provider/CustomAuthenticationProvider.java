package sju.capstone.docswant.security.authentication.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sju.capstone.docswant.security.authentication.token.CustomAuthenticationToken;
import sju.capstone.docswant.security.core.userdetails.CustomUserDetails;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final String BAD_CREDENTIALS_MESSAGE = "Password Mismatch";

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
        //TODO: PasswordEncoder 사용
        if (!userDetails.getPassword().equals(password)) {
            log.error("Password Mismatch. username = {}", userDetails.getUsername());
            throw new BadCredentialsException(BAD_CREDENTIALS_MESSAGE);
        }

        return new CustomAuthenticationToken(userDetails.getAccount(), null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CustomAuthenticationToken.class);
    }

}

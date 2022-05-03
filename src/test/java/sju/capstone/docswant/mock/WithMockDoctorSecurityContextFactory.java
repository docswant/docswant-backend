package sju.capstone.docswant.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.security.core.userdetails.CustomUserDetails;

import java.util.Arrays;

public class WithMockDoctorSecurityContextFactory implements WithSecurityContextFactory<WithMockDoctor> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SecurityContext createSecurityContext(WithMockDoctor annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Doctor doctor = Doctor.builder()
                .code("DOCTOR0001")
                .username("username")
                .password(passwordEncoder.encode("password"))
                .name("zooneon")
                .major("orthopedics")
                .build();

        CustomUserDetails principal = new CustomUserDetails(doctor, Arrays.asList(new SimpleGrantedAuthority("ACCOUNT_DOCTOR")));
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        context.setAuthentication(authentication);

        return context;
    }
}

package sju.capstone.docswant.mock;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockDoctorSecurityContextFactory.class)
public @interface WithMockDoctor {
}

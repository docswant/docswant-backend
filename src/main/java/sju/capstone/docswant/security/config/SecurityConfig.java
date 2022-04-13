package sju.capstone.docswant.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import sju.capstone.docswant.security.authentication.provider.CustomAuthenticationProvider;
import sju.capstone.docswant.security.web.common.CustomAuthenticationEntryPoint;
import sju.capstone.docswant.security.web.filter.CustomAuthenticationProcessingFilter;
import sju.capstone.docswant.security.web.handler.CustomAccessDeniedHandler;
import sju.capstone.docswant.security.web.handler.CustomAuthenticationFailureHandler;
import sju.capstone.docswant.security.web.handler.CustomAuthenticationSuccessHandler;

import java.util.Arrays;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String CORS_PATTERN = "/**";
    private static final String[] CORS_ALLOWED_METHODS = {"GET", "POST", "PUT", "DELETE", "OPTIONS"};

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest().permitAll()
        ;

        http
                .addFilterBefore(customAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Bean
    public CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter() throws Exception {
        CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter = new CustomAuthenticationProcessingFilter();
        customAuthenticationProcessingFilter.setAuthenticationManager(authenticationManagerBean());
        customAuthenticationProcessingFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        customAuthenticationProcessingFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        return customAuthenticationProcessingFilter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //TODO: origin 변경
        configuration.addAllowedOrigin(CorsConfiguration.ALL);
        configuration.addAllowedHeader(CorsConfiguration.ALL);
        configuration.setAllowedMethods(Arrays.asList(CORS_ALLOWED_METHODS));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(CORS_PATTERN, configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

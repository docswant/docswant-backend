package sju.capstone.docswant.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import sju.capstone.docswant.security.authentication.provider.CustomAuthenticationProvider;
import sju.capstone.docswant.security.web.common.CustomAuthenticationEntryPoint;
import sju.capstone.docswant.security.web.filter.CustomAuthenticationProcessingFilter;
import sju.capstone.docswant.security.web.filter.JwtAuthenticationFilter;
import sju.capstone.docswant.security.web.handler.CustomAccessDeniedHandler;
import sju.capstone.docswant.security.web.handler.CustomAuthenticationFailureHandler;
import sju.capstone.docswant.security.web.handler.CustomAuthenticationSuccessHandler;

@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] GET_PERMITTED_URLS = {
            "/api/v1/account/**",
            "/api/v1/doctor/validate/**",
            "/docs/**"
    };
    private static final String[] POST_PERMITTED_URLS = {
            "/api/v1/login",
            "/api/v1/doctor"
    };

    private final CorsConfigurationSource corsConfigurationSource;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, GET_PERMITTED_URLS).permitAll()
                .antMatchers(HttpMethod.POST, POST_PERMITTED_URLS).permitAll()
                .anyRequest().authenticated()
        ;

        http
                .addFilterBefore(customAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(customAuthenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
        ;
    }

    @Bean
    public CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter() throws Exception {
        CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter = new CustomAuthenticationProcessingFilter();
        customAuthenticationProcessingFilter.setAuthenticationManager(authenticationManagerBean());
        customAuthenticationProcessingFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        customAuthenticationProcessingFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        return customAuthenticationProcessingFilter;
    }

}

package sju.capstone.docswant.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import sju.capstone.docswant.security.authentication.provider.CustomAuthenticationProvider;
import sju.capstone.docswant.security.web.common.CustomAuthenticationEntryPoint;
import sju.capstone.docswant.security.web.filter.CustomAuthenticationProcessingFilter;
import sju.capstone.docswant.security.web.handler.CustomAccessDeniedHandler;
import sju.capstone.docswant.security.web.handler.CustomAuthenticationFailureHandler;
import sju.capstone.docswant.security.web.handler.CustomAuthenticationSuccessHandler;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsConfigurationSource corsConfigurationSource;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

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
                .anyRequest().permitAll()
        ;

        http
                .addFilterBefore(customAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(customAuthenticationProvider)
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

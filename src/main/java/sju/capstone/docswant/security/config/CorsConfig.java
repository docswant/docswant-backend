package sju.capstone.docswant.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {


    private static final String CORS_PATTERN = "/**";
    private static final String CLIENT_URL = "http://localhost:3000";
    private static final String[] CORS_ALLOWED_METHODS = {"GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"};

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //TODO: origin 변경
//        configuration.addAllowedOrigin(CLIENT_URL);
        configuration.addAllowedOrigin("http://192.168.0.8:3000");
        configuration.setAllowedHeaders(Arrays.asList(CorsConfiguration.ALL));
        configuration.setAllowedMethods(Arrays.asList(CORS_ALLOWED_METHODS));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(CORS_PATTERN, configuration);

        return source;
    }

}

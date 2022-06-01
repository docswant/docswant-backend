package sju.capstone.docswant.core.config;

import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.nio.charset.Charset;
import java.time.Duration;

@Configuration
public class WebClientConfig {

    @Value("${esl.url}")
    private String baseUrl;
    @Value("${esl.auth.basic_username}")
    private String username;
    @Value("${esl.auth.basic_password}")
    private String password;

    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000));
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(baseUrl)
                .defaultHeaders(header -> {
                    header.setContentType(MediaType.APPLICATION_JSON);
                    header.setBasicAuth(username, password, Charset.defaultCharset());
                })
                .build();
    }
}

package sju.capstone.docswant.infra.esl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class EslClient {

    private final WebClient webClient;

    @Value("${esl.uri.login}")
    private String loginUri;
    @Value("${esl.uri.edit}")
    private String editUri;
    @Value("${esl.auth.user_id}")
    private String userId;
    @Value("${esl.auth.user_password}")
    private String userPw;

    public Mono<Void> sendChangeRequest(EslDto.ChangeRequest requestDto) {
        return sendAuthRequest().flatMap(authResponse -> {
            String token = authResponse.getToken();
            return webClient.post()
                    .uri(editUri)
                    .header("token", token)
                    .body(Mono.just(requestDto), EslDto.ChangeRequest.class)
                    .retrieve()
                    .onStatus(httpStatus -> HttpStatus.BAD_REQUEST.equals(httpStatus),
                            clientResponse -> {
                                log.error("gateway data change request failed. status ={}", HttpStatus.BAD_REQUEST);
                                return Mono.empty();
                            })
                    .onStatus(httpStatus -> HttpStatus.INTERNAL_SERVER_ERROR.equals(httpStatus),
                            clientResponse -> {
                                log.error("gateway data change request failed. status ={}", HttpStatus.INTERNAL_SERVER_ERROR);
                                return Mono.empty();
                            })
                    .bodyToMono(Void.class)
                    .doOnSuccess(response -> log.info("gateway data change request success."));
        });
    }

    private Mono<EslDto.AuthResponse> sendAuthRequest() {
        EslDto.AuthRequest requestDto = EslDto.AuthRequest.builder()
                .userId(userId)
                .userPw(userPw)
                .build();
        return webClient.post()
                .uri(loginUri)
                .body(Mono.just(requestDto), EslDto.AuthRequest.class)
                .retrieve()
                .onStatus(httpStatus -> HttpStatus.BAD_REQUEST.equals(httpStatus),
                        clientResponse -> {
                            log.error("gateway auth request failed. status ={}", HttpStatus.BAD_REQUEST);
                            return Mono.empty();
                        })
                .onStatus(httpStatus -> HttpStatus.INTERNAL_SERVER_ERROR.equals(httpStatus),
                        clientResponse -> {
                            log.error("gateway auth request failed. status ={}", HttpStatus.INTERNAL_SERVER_ERROR);
                            return Mono.empty();
                        })
                .bodyToMono(EslDto.AuthResponse.class)
                .doOnSuccess(response -> log.info("gateway auth request success."));
    }

}

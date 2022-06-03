package sju.capstone.docswant.infra.esl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RequiredArgsConstructor
@Component
public class EslClient {

    private final WebClient webClient;

    @Value("${esl.url.change}")
    private String changeUrl;

    public Disposable sendChangeRequest(EslDto.ChangeRequest requestDto) {
        return webClient.post()
                .uri(changeUrl)
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
                .doOnSuccess(response -> log.info("gateway data change request success."))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();
    }
}

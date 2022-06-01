package sju.capstone.docswant.infra.esl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.rounding.model.entity.Rounding;

@RequiredArgsConstructor
public class EslService {

    private final WebClient webClient;

    @Value("${esl.uri.login}")
    private String loginUri;
    @Value("${esl.uri.edit}")
    private String editUri;
    @Value("${esl.auth.user_id}")
    private String userId;
    @Value("${esl.auth.user_password}")
    private String userPw;

    public void sendChangeRequest(Patient patient, Rounding rounding) {
        EslDto.ChangeContent changeContent = EslDto.ChangeContent.builder()
                .tagId(patient.getId())
                .patientName(patient.getName())
                .doctorName(patient.getDoctor().getName())
                .surgeryDday()
                .dischargeDday()
                .roundingTime()
                .roundingOrder()
                .build();

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
                .bodyToMono(EslDto.AuthResponse.class);
    }
}

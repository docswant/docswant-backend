package sju.capstone.docswant.domain.member.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import sju.capstone.docswant.common.IntegrationTest;
import sju.capstone.docswant.common.factory.DtoFactory;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.domain.member.model.dto.PatientDto;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sju.capstone.docswant.common.utils.ApiDocumentUtils.getDocumentRequest;
import static sju.capstone.docswant.common.utils.ApiDocumentUtils.getDocumentResponse;

class PatientControllerTest extends IntegrationTest {

    @Test
    void 환자_등록_API_테스트() throws Exception {
        //given
        String registerUrl = "/api/v1/patient";
        PatientDto.Request requestDto = DtoFactory.getPatientRequestDto();

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.post(registerUrl)
                .header("Authorization", "Bearer " + jwtToken.createAccessToken(EntityFactory.getDoctorEntity()))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
        );

        //then
        actions
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("patient/register",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("사용자 토큰")
                        ),
                        requestFields(
                                fieldWithPath("code").description("환자 코드"),
                                fieldWithPath("name").description("환자 이름"),
                                fieldWithPath("birthDate").description("환자 생년월일"),
                                fieldWithPath("hospitalizationDate").description("환자 입원날짜"),
                                fieldWithPath("diseaseName").description("환자 병명"),
                                fieldWithPath("hospitalRoom").description("환자 병실 호수")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.code").description("환자 코드"),
                                fieldWithPath("data.username").description("환자 사용자명"),
                                fieldWithPath("data.name").description("환자 이름"),
                                fieldWithPath("data.birthDate").description("환자 생년월일"),
                                fieldWithPath("data.hospitalizationDate").description("환자 입원날짜"),
                                fieldWithPath("data.diseaseName").description("환자 병명"),
                                fieldWithPath("data.hospitalRoom").description("환자 병실 호수")
                        )
                ))
        ;
    }
}
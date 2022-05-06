package sju.capstone.docswant.domain.member.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import sju.capstone.docswant.common.MockMvcTest;
import sju.capstone.docswant.common.factory.DtoFactory;
import sju.capstone.docswant.domain.member.model.dto.PatientDto;
import sju.capstone.docswant.mock.WithMockDoctor;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sju.capstone.docswant.utils.ApiDocumentUtils.getDocumentRequest;
import static sju.capstone.docswant.utils.ApiDocumentUtils.getDocumentResponse;

class PatientControllerTest extends MockMvcTest {

    @WithMockDoctor
    @Test
    void 환자_등록_API_테스트() throws Exception {
        //given
        String registerUrl = "/api/v1/patient";
        String authorizationHeader = "Authorization";
        String bearerToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoicmVmcmVzaCB0b2tlbiIsInN1YiI6InVzZXJuYW1lIiwiaWF0IjoxNjUxNTk1Mjk2LCJleHAiOjE2NTQxODcyOTZ9.gKvFVjbooMDIqO4qUP9FPaqSUjfZP6tT8RwJwu43i3Y";
        PatientDto.Request requestDto = DtoFactory.getPatientRegisterRequestDto();

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.post(registerUrl)
                .header(authorizationHeader, bearerToken)
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
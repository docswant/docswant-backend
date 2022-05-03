package sju.capstone.docswant.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
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

@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
class PatientControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockDoctor
    @Test
    void 환자_등록_API_테스트() throws Exception {
        //given
        String registerUrl = "/api/v1/patient";
        String authorizationHeader = "Authorization";
        String bearerToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbiB0eXBlIjoicmVmcmVzaCB0b2tlbiIsInN1YiI6InVzZXJuYW1lIiwiaWF0IjoxNjUxNTk1Mjk2LCJleHAiOjE2NTQxODcyOTZ9.gKvFVjbooMDIqO4qUP9FPaqSUjfZP6tT8RwJwu43i3Y";
        PatientDto.Request requestDto = PatientDto.Request.builder()
                .code("PATIENT001")
                .name("zooneon")
                .birthDate(LocalDate.of(1997, 8, 26))
                .hospitalizationDate(LocalDate.of(2022, 5, 3))
                .diseaseName("COVID-19")
                .hospitalRoom(302)
                .build();

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
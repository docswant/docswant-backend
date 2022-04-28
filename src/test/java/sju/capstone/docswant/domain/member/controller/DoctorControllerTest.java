package sju.capstone.docswant.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import sju.capstone.docswant.domain.member.model.dto.DoctorDto;
import sju.capstone.docswant.domain.member.service.doctor.DoctorService;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sju.capstone.docswant.utils.ApiDocumentUtils.getDocumentRequest;
import static sju.capstone.docswant.utils.ApiDocumentUtils.getDocumentResponse;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
class DoctorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 의사_코드_검증_API_테스트() throws Exception {
        //given
        String validateUrl = "/api/v1/doctor/validate?code={code}";
        String code = "DOCTOR0001";

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.get(validateUrl, code));

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("doctor/validate",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("code").description("의사 코드")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터")
                        )
                ))
        ;
    }

    @Test
    void 의사_회원가입_API_테스트() throws Exception {
        //given
        String registerUrl = "/api/v1/doctor";
        DoctorDto.Request requestDto = DoctorDto.Request.builder().code("DOCTOR001").username("doctor").password("password").name("zooneon").major("orthopedics").build();

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.post(registerUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
        );

        //then
        actions
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("doctor/register",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("code").description("의사 코드"),
                                fieldWithPath("username").description("의사 사용자명"),
                                fieldWithPath("password").description("의사 비밀번호"),
                                fieldWithPath("name").description("의사 이름"),
                                fieldWithPath("major").description("의사 전공")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.code").description("의사 코드"),
                                fieldWithPath("data.name").description("의사 이름"),
                                fieldWithPath("data.major").description("의사 전공")
                        )
                ))
        ;
    }
}
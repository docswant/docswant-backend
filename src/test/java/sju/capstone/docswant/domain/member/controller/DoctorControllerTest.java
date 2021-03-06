package sju.capstone.docswant.domain.member.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import sju.capstone.docswant.common.IntegrationTest;
import sju.capstone.docswant.common.factory.DtoFactory;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.domain.member.model.dto.DoctorDto;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sju.capstone.docswant.common.utils.ApiDocumentUtils.getDocumentRequest;
import static sju.capstone.docswant.common.utils.ApiDocumentUtils.getDocumentResponse;

class DoctorControllerTest extends IntegrationTest {

    @Test
    void 의사_코드_검증_API_테스트() throws Exception {
        //given
        String validateUrl = "/api/v1/doctor/validate/{code}";
        String code = "DOCTOR001";

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.get(validateUrl, code));

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("doctor/validate",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
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
        DoctorDto.Request requestDto = DtoFactory.getDoctorRequestDto();

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
                                fieldWithPath("data.username").description("의사 사용자명"),
                                fieldWithPath("data.name").description("의사 이름"),
                                fieldWithPath("data.major").description("의사 전공")
                        )
                ))
        ;
    }

    @Test
    void 의사_정보수정_API_테스트() throws Exception {
        //given
        String updateUrl = "/api/v1/doctor/{code}";
        DoctorDto.Request requestDto = DtoFactory.getDoctorUpdateRequestDto();

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.patch(updateUrl, requestDto.getCode())
                .header("Authorization", "Bearer " + jwtToken.createAccessToken(EntityFactory.getDoctorEntity()))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("doctor/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("사용자 토큰")
                        ),
                        requestFields(
                                fieldWithPath("code").description("의사 코드"),
                                fieldWithPath("username").description("의사 사용자명"),
                                fieldWithPath("password").description("의사 비밀번호").optional(),
                                fieldWithPath("name").description("의사 이름"),
                                fieldWithPath("major").description("의사 전공")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.code").description("의사 코드"),
                                fieldWithPath("data.username").description("의사 사용자명"),
                                fieldWithPath("data.name").description("의사 이름"),
                                fieldWithPath("data.major").description("의사 전공")
                        )
                ))
        ;
    }
}
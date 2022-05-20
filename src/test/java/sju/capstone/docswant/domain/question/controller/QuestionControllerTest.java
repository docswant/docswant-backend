package sju.capstone.docswant.domain.question.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import sju.capstone.docswant.common.IntegrationTest;
import sju.capstone.docswant.common.factory.DtoFactory;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.question.model.dto.QuestionDto;
import sju.capstone.docswant.domain.question.repository.QuestionRepository;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sju.capstone.docswant.common.utils.ApiDocumentUtils.getDocumentRequest;
import static sju.capstone.docswant.common.utils.ApiDocumentUtils.getDocumentResponse;

class QuestionControllerTest extends IntegrationTest {

    private Patient patient = EntityFactory.getPatientEntity();
    private Doctor doctor = EntityFactory.getDoctorEntity();

    @Test
    void 질문_생성_API_테스트() throws Exception {
        //given
        String createUrl = "/api/v1/patient/{code}/question";
        QuestionDto.Request requestDto = DtoFactory.getQuestionRequestDto();

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.post(createUrl, patient.getCode())
                .header("Authorization", "Bearer " + jwtToken.createAccessToken(doctor))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
        );

        //then
        actions
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("question/create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("의사 토큰")
                        ),
                        pathParameters(
                                parameterWithName("code").description("환자 코드")
                        ),
                        requestFields(
                                fieldWithPath("content").description("질문 내용")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.id").description("질문 id"),
                                fieldWithPath("data.content").description("질문 내용"),
                                fieldWithPath("data.answerStatus").description("질문 응답 상태")
                        )
                ))
        ;
    }

    @Test
    void 질문_수정_API_테스트() throws Exception {
        //given
        String updateUrl = "/api/v1/patient/{code}/question/{id}/content";
        QuestionDto.UpdateRequest requestDto = DtoFactory.getQuestionUpdateRequestDto();

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.patch(updateUrl, patient.getCode(), 1L)
                .header("Authorization", "Bearer " + jwtToken.createAccessToken(doctor))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("question/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("의사 토큰")
                        ),
                        pathParameters(
                                parameterWithName("code").description("환자 코드"),
                                parameterWithName("id").description("수정할 질문 id")
                        ),
                        requestFields(
                                fieldWithPath("content").description("수정 질문 내용")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.id").description("질문 id"),
                                fieldWithPath("data.content").description("질문 내용"),
                                fieldWithPath("data.answerStatus").description("질문 응답 상태")
                        )
                ))
        ;
    }

    @Autowired
    QuestionRepository questionRepository;

    @Test
    void 질문_응답_API_테스트() throws Exception {
        //given
        String answerUrl = "/api/v1/patient/{code}/question/{id}/answer";
        QuestionDto.AnswerRequest requestDto = DtoFactory.getQuestionAnswerRequestDto();

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.patch(answerUrl, patient.getCode(), 1L)
                .header("Authorization", "Bearer " + jwtToken.createAccessToken(patient))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("question/answer",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("환자 토큰")
                        ),
                        pathParameters(
                                parameterWithName("code").description("환자 코드"),
                                parameterWithName("id").description("응답할 질문 id")
                        ),
                        requestFields(
                                fieldWithPath("answer").description("질문 응답 내용")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.id").description("질문 id"),
                                fieldWithPath("data.content").description("질문 내용"),
                                fieldWithPath("data.answer").description("질문 응답 내용"),
                                fieldWithPath("data.answerStatus").description("질문 응답 상태")
                        )
                ))
        ;
    }

    @Test
    void 질문_삭제_API_테스트() throws Exception {
        //given
        String deleteUrl = "/api/v1/patient/{code}/question/{id}";

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.delete(deleteUrl, patient.getCode(), 1L)
                .header("Authorization", "Bearer " + jwtToken.createAccessToken(doctor))
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("question/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("의사 토큰")
                        ),
                        pathParameters(
                                parameterWithName("code").description("환자 코드"),
                                parameterWithName("id").description("삭제할 질문 id")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간")
                        )
                ))
        ;
    }

    @Test
    void 질문_조회_API_테스트() throws Exception {
        //given
        String findUrl = "/api/v1/patient/{code}/question/{id}";

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.get(findUrl, patient.getCode(), 2L)
                .header("Authorization", "Bearer " + jwtToken.createAccessToken(doctor))
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("question/find",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("사용자 토큰")
                        ),
                        pathParameters(
                                parameterWithName("code").description("환자 코드"),
                                parameterWithName("id").description("조회할 질문 id")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.id").description("질문 id"),
                                fieldWithPath("data.content").description("질문 내용"),
                                fieldWithPath("data.answer").description("질문 응답 내용").optional(),
                                fieldWithPath("data.answerStatus").description("질문 응답 상태")
                        )
                ))
        ;
    }

    @Test
    void 질문_리스트_조회_API_테스트() throws Exception {
        //given
        int page = 1;
        int size = 3;
        String findAllUrl = "/api/v1/patient/{code}/question?page={page}&size={size}";

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.get(findAllUrl, patient.getCode(), page, size)
                .header("Authorization", "Bearer " + jwtToken.createAccessToken(doctor))
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("question/findAll",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("사용자 토큰")
                        ),
                        pathParameters(
                                parameterWithName("code").description("환자 코드")
                        ),
                        requestParameters(
                                parameterWithName("page").description("요청 페이지"),
                                parameterWithName("size").description("요청 개수")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.page").description("현재 페이지"),
                                fieldWithPath("data.hasNext").description("다음 페이지 존재 여부"),
                                fieldWithPath("data.content").description("질문 리스트"),
                                fieldWithPath("data.content[*].id").description("환자 코드"),
                                fieldWithPath("data.content[*].content").description("질문 내용"),
                                fieldWithPath("data.content[*].answer").description("질문 응답 내용").optional(),
                                fieldWithPath("data.content[*].answerStatus").description("질문 응답 상태")
                        )
                ))
        ;
    }
}
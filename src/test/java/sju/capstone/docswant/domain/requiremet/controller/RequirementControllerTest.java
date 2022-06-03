package sju.capstone.docswant.domain.requiremet.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import sju.capstone.docswant.common.IntegrationTest;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.requirement.model.dto.RequirementDto;

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

class RequirementControllerTest extends IntegrationTest {

    @Test
    void 문의사항_등록_API_테스트() throws Exception {
        //given
        String registerUrl = "/api/v1/patient/{code}/requirement";
        Patient patient = EntityFactory.getPatientEntity();
        RequirementDto.Request requestDto = RequirementDto.Request.builder().title("title").content("content").build();

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.post(registerUrl, patient.getCode())
                .header("Authorization", "Bearer " + jwtToken.createAccessToken(patient))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
        );

        //then
        actions
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("requirement/register",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("환자 토큰")
                        ),
                        pathParameters(
                                parameterWithName("code").description("환자 코드")
                        ),
                        requestFields(
                                fieldWithPath("title").description("문의 사항 제목"),
                                fieldWithPath("content").description("문의 사항 내용")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.id").description("문의사항 ID"),
                                fieldWithPath("data.title").description("문의사항 제목"),
                                fieldWithPath("data.content").description("문의사항 내용"),
                                fieldWithPath("data.status").description("문의사항 상태")
                        )
                )
                );
    }

    @Test
    void 문의사항_수정_API_테스트() throws Exception {
        //given
        String updateUrl = "/api/v1/patient/{code}/requirement/{id}/content";
        Patient patient = EntityFactory.getPatientEntity();
        RequirementDto.UpdateRequest requestDto =  RequirementDto.UpdateRequest.builder().content("update content").build();

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.patch(updateUrl, patient.getCode(), 1L)
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
                .andDo(document("requirement/update",
                        getDocumentRequest(),
                        getDocumentResponse(),

                        requestHeaders(
                                headerWithName("Authorization").description("환자 토큰")
                        ),
                        pathParameters(
                                parameterWithName("code").description("환자 코드"),
                                parameterWithName("id").description("수정할 문의사항 id")
                        ),
                        requestFields(
                                fieldWithPath("content").description("수정한 문의사항 내용")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.id").description("문의사항 id"),
                                fieldWithPath("data.title").description("문의사항 제목"),
                                fieldWithPath("data.content").description("문의사항 내용"),
                                fieldWithPath("data.status").description("문의사항 확인 상태")
                        )
                ))
        ;
    }

    @Test
    void 문의사항_삭제_API_테스트() throws Exception {
        //given
        String deleteUrl = "/api/v1/patient/{code}/requirement/{id}";
        Patient patient = EntityFactory.getPatientEntity();

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.delete(deleteUrl, patient.getCode(), 1L)
                .header("Authorization", "Bearer " + jwtToken.createAccessToken(patient))
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("requirement/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("환자 토큰")
                        ),
                        pathParameters(
                                parameterWithName("code").description("환자 코드"),
                                parameterWithName("id").description("삭제할 문의사항 id")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간")
                        )
                ))
        ;
    }

    @Test
    void 문의사항_조회_API_테스트() throws Exception {
        //given
        String findUrl = "/api/v1/patient/{code}/requirement/{id}";
        Long id = 1L;
        Patient patient = EntityFactory.getPatientEntity();
        Doctor doctor = EntityFactory.getDoctorEntity();

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.get(findUrl, patient.getCode(), id)
                .header("Authorization", "Bearer " + jwtToken.createAccessToken(doctor))
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("requirement/find",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("사용자 토큰")
                        ),
                        pathParameters(
                                parameterWithName("code").description("환자 코드"),
                                parameterWithName("id").description("문의사항 id")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.id").description("문의사항 id"),
                                fieldWithPath("data.title").description("문의사항 제목"),
                                fieldWithPath("data.content").description("문의사항 내용"),
                                fieldWithPath("data.status").description("문의사항 읽음 상태")
                        )
                )
                );
    }

    @Test
    void 문의사항_리스트조회_API_테스트() throws Exception {
        //given
        int page = 1;
        int size = 3;
        String findAllUrl = "/api/v1/patient/{code}/requirement?page={page}&size={size}";
        Patient patient = EntityFactory.getPatientEntity();

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.get(findAllUrl, patient.getCode(), page, size)
                .header("Authorization", "Bearer " + jwtToken.createAccessToken(patient))
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("requirement/findAll",
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
                                fieldWithPath("data.content[*].id").description("문의사항 id"),
                                fieldWithPath("data.content[*].title").description("문의사항 제목"),
                                fieldWithPath("data.content[*].content").description("문의사항 내용"),
                                fieldWithPath("data.content[*].status").description("문의사항 확인 상태")
                        )
                ))
        ;
    }

}

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
import java.time.LocalDate;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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
                                headerWithName("Authorization").description("의사 토큰")
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
                                fieldWithPath("data.name").description("환자 이름"),
                                fieldWithPath("data.birthDate").description("환자 생년월일"),
                                fieldWithPath("data.hospitalizationDate").description("환자 입원날짜"),
                                fieldWithPath("data.diseaseName").description("환자 병명"),
                                fieldWithPath("data.hospitalRoom").description("환자 병실 호수")
                        )
                ))
        ;
    }

    @Test
    void 환자_정보수정_API_테스트() throws Exception {
        //given
        String code = "PATIENT001";
        String updateUrl = "/api/v1/patient/{code}";
        PatientDto.UpdateRequest requestDto = DtoFactory.getPatientUpdateRequestDto();

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.patch(updateUrl, code)
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
                .andDo(document("patient/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("사용자 토큰")
                        ),
                        pathParameters(
                                parameterWithName("code").description("수정할 환자 코드")
                        ),
                        requestFields(
                                fieldWithPath("username").description("환자 사용자명").optional(),
                                fieldWithPath("password").description("환자 비밀번호").optional(),
                                fieldWithPath("name").description("환자 이름").optional(),
                                fieldWithPath("birthDate").description("환자 생년월일").optional(),
                                fieldWithPath("hospitalizationDate").description("환자 입원날짜").optional(),
                                fieldWithPath("surgeryDate").description("환자 수술날짜").optional(),
                                fieldWithPath("dischargeDate").description("환자 퇴원날짜").optional(),
                                fieldWithPath("diseaseName").description("환자 병명").optional(),
                                fieldWithPath("hospitalRoom").description("환자 병실 호수").optional()
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.code").description("환자 코드"),
                                fieldWithPath("data.name").description("환자 이름"),
                                fieldWithPath("data.birthDate").description("환자 생년월일"),
                                fieldWithPath("data.hospitalizationDate").description("환자 입원날짜"),
                                fieldWithPath("data.surgeryDate").description("환자 수술날짜").optional(),
                                fieldWithPath("data.dischargeDate").description("환자 퇴원날짜").optional(),
                                fieldWithPath("data.diseaseName").description("환자 병명"),
                                fieldWithPath("data.hospitalRoom").description("환자 병실 호수")
                        )
                ))
        ;
    }

    @Test
    void 환자_삭제_API_테스트() throws Exception {
        //given
        String deleteUrl = "/api/v1/patient/{code}";
        String code = "PATIENT001";

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.delete(deleteUrl, code)
                .header("Authorization", "Bearer " + jwtToken.createAccessToken(EntityFactory.getDoctorEntity()))
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patient/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("의사 토큰")
                        ),
                        pathParameters(
                                parameterWithName("code").description("삭제할 환자 코드")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간")
                        )
                ))
        ;
    }

    @Test
    void 환자_조회_API_테스트() throws Exception {
        //given
        String findUrl = "/api/v1/patient/{code}";
        String code = "PATIENT001";

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.get(findUrl, code)
                .header("Authorization", "Bearer " + jwtToken.createAccessToken(EntityFactory.getDoctorEntity()))
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patient/find",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("사용자 토큰")
                        ),
                        pathParameters(
                                parameterWithName("code").description("조회할 환자 코드")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.code").description("환자 코드"),
                                fieldWithPath("data.name").description("환자 이름"),
                                fieldWithPath("data.birthDate").description("환자 생년월일"),
                                fieldWithPath("data.hospitalizationDate").description("환자 입원날짜"),
                                fieldWithPath("data.surgeryDate").description("환자 수술날짜").optional(),
                                fieldWithPath("data.dischargeDate").description("환자 퇴원날짜").optional(),
                                fieldWithPath("data.diseaseName").description("환자 병명"),
                                fieldWithPath("data.hospitalRoom").description("환자 병실 호수")
                        )
                ))
        ;
    }

    @Test
    void 환자_회진_조회_테스트() throws Exception {
        //given
        String code = "PATIENT001";
        LocalDate today = LocalDate.of(2022, 5, 17);
        String findWithRoundingUrl = "/api/v1/patient/{code}/rounding?date={date}";

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.get(findWithRoundingUrl, code, today)
                .header("Authorization", "Bearer " + jwtToken.createAccessToken(EntityFactory.getPatientEntity()))
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patient/findWithRounding",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("환자 토큰")
                        ),
                        pathParameters(
                                parameterWithName("code").description("조회할 환자 코드")
                        ),
                        requestParameters(
                                parameterWithName("date").description("조회 날짜")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.code").description("환자 코드"),
                                fieldWithPath("data.patientName").description("환자 이름"),
                                fieldWithPath("data.birthDate").description("환자 생년월일"),
                                fieldWithPath("data.hospitalizationDate").description("환자 입원날짜"),
                                fieldWithPath("data.surgeryDate").description("환자 수술날짜").optional(),
                                fieldWithPath("data.dischargeDate").description("환자 퇴원날짜").optional(),
                                fieldWithPath("data.diseaseName").description("환자 병명"),
                                fieldWithPath("data.hospitalRoom").description("환자 병실 호수"),
                                fieldWithPath("data.doctorName").description("담당 의사 이름"),
                                fieldWithPath("data.doctorMajor").description("담당 의사 전공"),
                                fieldWithPath("data.roundingTime").description("환자 회진 시간").optional(),
                                fieldWithPath("data.roundsWaitingOrder").description("회진 대기 순서").optional()
                        )
                ))
        ;
    }

    @Test
    void 환자_리스트_조회_API_테스트() throws Exception {
        //given
        int page = 1;
        int size = 3;
        String findAllUrl = "/api/v1/patient?page={page}&size={size}";

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.get(findAllUrl, page, size)
                .header("Authorization", "Bearer " + jwtToken.createAccessToken(EntityFactory.getDoctorEntity()))
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patient/findAll",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("의사 토큰")
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
                                fieldWithPath("data.content").description("환자 리스트"),
                                fieldWithPath("data.content[*].code").description("환자 코드"),
                                fieldWithPath("data.content[*].name").description("환자 이름"),
                                fieldWithPath("data.content[*].birthDate").description("환자 생년월일"),
                                fieldWithPath("data.content[*].hospitalizationDate").description("환자 입원날짜"),
                                fieldWithPath("data.content[*].surgeryDate").description("환자 수술날짜").optional(),
                                fieldWithPath("data.content[*].dischargeDate").description("환자 퇴원날짜").optional(),
                                fieldWithPath("data.content[*].diseaseName").description("환자 병명"),
                                fieldWithPath("data.content[*].hospitalRoom").description("환자 병실 호수"),
                                fieldWithPath("data.content[*].hasUnreadRequirement").description("읽지 않은 문의사항 존재 여부")
                        )
                ))
        ;
    }
}
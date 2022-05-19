package sju.capstone.docswant.domain.rounding.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import sju.capstone.docswant.common.IntegrationTest;
import sju.capstone.docswant.common.factory.DtoFactory;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.rounding.model.dto.RoundingDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sju.capstone.docswant.common.utils.ApiDocumentUtils.getDocumentRequest;
import static sju.capstone.docswant.common.utils.ApiDocumentUtils.getDocumentResponse;

class RoundingControllerTest extends IntegrationTest {

    private Doctor doctor = EntityFactory.getDoctorEntity();

    @Test
    void 회진_생성_API_테스트() throws Exception {
        //given
        String createUrl = "/api/v1/doctor/{code}/rounding";
        RoundingDto.Request requestDto = DtoFactory.getRoundingRequestDto();

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.post(createUrl, doctor.getCode())
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
                .andDo(document("rounding/create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("의사 토큰")
                        ),
                        pathParameters(
                                parameterWithName("code").description("의사 코드")
                        ),
                        requestFields(
                                fieldWithPath("code").description("환자 코드"),
                                fieldWithPath("roundingDate").description("회진 날짜"),
                                fieldWithPath("roundingTime").description("회진 시간")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.id").description("회진 id"),
                                fieldWithPath("data.roundingDate").description("회진 날짜"),
                                fieldWithPath("data.patientName").description("환자 이름"),
                                fieldWithPath("data.roundingTime").description("회진 시간"),
                                fieldWithPath("data.roundingStatus").description("회진 상태")
                        )
                ))
        ;
    }

    @Test
    void 회진_수정_API_테스트() throws Exception {
        //given
        String updateUrl = "/api/v1/doctor/{code}/rounding/{id}";
        RoundingDto.UpdateRequest requestDto = DtoFactory.getRoundingUpdateRequestDto();

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.patch(updateUrl, doctor.getCode(), 1L)
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
                .andDo(document("rounding/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("의사 토큰")
                        ),
                        pathParameters(
                                parameterWithName("code").description("의사 코드"),
                                parameterWithName("id").description("수정할 회진 id")
                        ),
                        requestFields(
                                fieldWithPath("roundingDate").description("수정 회진 날짜").optional(),
                                fieldWithPath("roundingTime").description("수정 회진 시간").optional()
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.id").description("회진 id"),
                                fieldWithPath("data.roundingDate").description("회진 날짜"),
                                fieldWithPath("data.patientName").description("환자 이름"),
                                fieldWithPath("data.roundingTime").description("회진 시간"),
                                fieldWithPath("data.roundingStatus").description("회진 상태")
                        )
                ))
        ;
    }

    @Test
    void 회진_삭제_API_테스트() throws Exception {
        //given
        String deleteUrl = "/api/v1/doctor/{code}/rounding/{id}";

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.delete(deleteUrl,doctor.getCode(), 1L)
                .header("Authorization", "Bearer " + jwtToken.createAccessToken(doctor))
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("rounding/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("의사 토큰")
                        ),
                        pathParameters(
                                parameterWithName("code").description("의사 코드"),
                                parameterWithName("id").description("삭제할 회진 id")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간")
                        )
                ))
        ;
    }

    @Test
    void 회진_전체_삭제_API_테스트() throws Exception {
        //given
        String deleteAllUrl = "/api/v1/doctor/{code}/rounding?ids=1,2,3";

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.delete(deleteAllUrl, doctor.getCode())
                .header("Authorization", "Bearer " + jwtToken.createAccessToken(doctor))
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("rounding/deleteAll",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("의사 토큰")
                        ),
                        pathParameters(
                                parameterWithName("code").description("의사 코드")
                        ),
                        requestParameters(
                                parameterWithName("ids").description("삭제할 회진 id 리스트")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간")
                        )
                ))
        ;
    }

    @Test
    void 회진_상태_변경_API_테스트() throws Exception {
        //given
        String changeStatusUrl = "/api/v1/doctor/{code}/rounding/{id}/status";

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.patch(changeStatusUrl, doctor.getCode(), 1L)
                .header("Authorization", "Bearer " + jwtToken.createAccessToken(doctor))
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("rounding/changeStatus",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("의사 토큰")
                        ),
                        pathParameters(
                                parameterWithName("code").description("의사 코드"),
                                parameterWithName("id").description("변경할 회진 id")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.id").description("회진 id"),
                                fieldWithPath("data.roundingDate").description("회진 날짜"),
                                fieldWithPath("data.patientName").description("환자 이름"),
                                fieldWithPath("data.roundingTime").description("회진 시간"),
                                fieldWithPath("data.roundingStatus").description("회진 상태")
                        )
                ))
        ;
    }

    @Test
    void 회진_조회_API_테스트() throws Exception {
        //given
        String findUrl = "/api/v1/doctor/{code}/rounding/{id}";

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.get(findUrl, doctor.getCode(), 1L)
                .header("Authorization", "Bearer " + jwtToken.createAccessToken(doctor))
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("rounding/find",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("사용자 토큰")
                        ),
                        pathParameters(
                                parameterWithName("code").description("의사 코드"),
                                parameterWithName("id").description("조회할 회진 id")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.id").description("회진 id"),
                                fieldWithPath("data.roundingDate").description("회진 날짜"),
                                fieldWithPath("data.patientName").description("환자 이름"),
                                fieldWithPath("data.roundingTime").description("회진 시간"),
                                fieldWithPath("data.roundingStatus").description("회진 상태")
                        )
                ))
        ;
    }

    @Test
    void 날짜별_회진_조회_API_테스트() throws Exception {
        //given
        String findAllApi = "/api/v1/doctor/{code}/rounding?date={date}";

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.get(findAllApi, doctor.getCode(), LocalDate.of(2022, 5, 17))
                .header("Authorization", "Bearer " + jwtToken.createAccessToken(doctor))
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("rounding/findAllByDate",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("의사 토큰")
                        ),
                        pathParameters(
                                parameterWithName("code").description("의사 코드")
                        ),
                        requestParameters(
                                parameterWithName("date").description("회진 날짜")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data[*].hospitalRoom").description("병실 호수"),
                                fieldWithPath("data[*].roundings").description("회진 정보"),
                                fieldWithPath("data[*].roundings[*].id").description("회진 id"),
                                fieldWithPath("data[*].roundings[*].roundingDate").description("회진 날짜"),
                                fieldWithPath("data[*].roundings[*].patientName").description("환자 이름"),
                                fieldWithPath("data[*].roundings[*].roundingTime").description("회진 시간"),
                                fieldWithPath("data[*].roundings[*].roundingStatus").description("회진 상태")
                        )
                ))
        ;
    }
}
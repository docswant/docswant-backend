package sju.capstone.docswant.domain.member.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import sju.capstone.docswant.common.IntegrationTest;
import sju.capstone.docswant.common.factory.DtoFactory;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.domain.member.model.dto.AccountDto;
import sju.capstone.docswant.domain.member.model.entity.Account;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.member.service.account.AccountService;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sju.capstone.docswant.common.utils.ApiDocumentUtils.getDocumentRequest;
import static sju.capstone.docswant.common.utils.ApiDocumentUtils.getDocumentResponse;

class AccountControllerTest extends IntegrationTest {

    @MockBean
    private AccountService accountService;

    @Test
    void 로그인_API_테스트() throws Exception {
        //given
        String loginUrl = "/api/v1/login";
        AccountDto.Request requestDto = DtoFactory.getAccountRequestDto();

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.post(loginUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("account/login",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("username").description("사용자명"),
                                fieldWithPath("password").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.code").description("계정 코드"),
                                fieldWithPath("data.accountType").description("계정 타입"),
                                fieldWithPath("data.accessToken").description("JWT access token"),
                                fieldWithPath("data.refreshToken").description("JWT refresh token")
                        )
                ))
        ;
    }

    @Test
    void 사용자명_중복_확인_API_테스트() throws Exception {
        //given
        String requestUrl = "/api/v1/account/exists/{username}";
        String username = "username";

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.get(requestUrl, username));

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("account/exists",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("username").description("사용자명")
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
    void 토근_재발급_API_테스트() throws Exception {
        //given
        String requestUrl = "/api/v1/account/{code}?token={token}";
        String code = "code";
        String refreshToken = "REFRESH_TOKEN";
        given(accountService.reissueAccessToken(any(String.class), any(String.class))).willReturn("ACCESS_TOKEN");

        //when
        ResultActions actions = mvc.perform(RestDocumentationRequestBuilders.get(requestUrl, code, refreshToken));

        //then
        actions
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("account/reissueToken",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("code").description("사용자 코드")
                        ),
                        requestParameters(
                                parameterWithName("token").description("refresh 토큰")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("timestamp").description("응답 시간"),
                                fieldWithPath("data").description("응답 데이터")
                        )
                ))
        ;
    }
}

package sju.capstone.docswant.domain.member.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import sju.capstone.docswant.common.MockMvcTest;
import sju.capstone.docswant.common.factory.DtoFactory;
import sju.capstone.docswant.domain.member.model.dto.AccountDto;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sju.capstone.docswant.common.utils.ApiDocumentUtils.getDocumentRequest;
import static sju.capstone.docswant.common.utils.ApiDocumentUtils.getDocumentResponse;

class AccountControllerTest extends MockMvcTest {

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

}

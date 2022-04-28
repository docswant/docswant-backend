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
import sju.capstone.docswant.domain.member.model.dto.AccountDto;

import java.nio.charset.StandardCharsets;

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
class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 로그인_API_테스트() throws Exception {

        //given
        String loginUrl = "/api/v1/login";
        String username = "username";
        String password = "password";
        AccountDto.Request requestDto = AccountDto.Request.builder().username(username).password(password).build();

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
        String requestUrl = "/api/v1/account/exists?username={username}";
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
                        requestParameters(
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

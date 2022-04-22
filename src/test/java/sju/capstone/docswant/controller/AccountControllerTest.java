package sju.capstone.docswant.controller;

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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import sju.capstone.docswant.security.web.dto.AccountDto;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sju.capstone.docswant.utils.ApiDocumentUtils.getDocumentRequest;
import static sju.capstone.docswant.utils.ApiDocumentUtils.getDocumentResponse;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Sql(statements = {
            "INSERT INTO account(account_code, account_username, account_password, account_type, created_at, updated_at) values(\"DOCTOR0001\", \"username\", \"{bcrypt}$2a$10$fMZSzFrq4nrj8QxAX6ISFOQ11vIOMExhyodCXtHvwyRCmUTZMBRmy\", \"ACCOUNT_DOCTOR\", \"2022-04-16 12:00:00.000000\", \"2022-04-16 12:00:00.000000\");",
            "INSERT INTO doctor(doctor_major, doctor_name, doctor_code) values (\"NONE\", \"Kim\", \"DOCTOR0001\");"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void 로그인_API_테스트() throws Exception {
        //given
        String loginUrl = "/api/v1/login";
        String username = "username";
        String password = "password";
        AccountDto.Request requestDto = new AccountDto.Request(username, password);

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
                                fieldWithPath("code").description("계정 코드"),
                                fieldWithPath("accountType").description("계정 타입"),
                                fieldWithPath("accessToken").description("JWT access token"),
                                fieldWithPath("refreshToken").description("JWT refresh token")
                        )
                ))
        ;
    }

}

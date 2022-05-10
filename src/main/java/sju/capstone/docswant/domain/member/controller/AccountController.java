package sju.capstone.docswant.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sju.capstone.docswant.common.format.ResponseFormat;
import sju.capstone.docswant.domain.member.service.account.AccountService;

@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
@RestController
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/exists/{username}")
    public ResponseEntity<ResponseFormat<Boolean>> existsUsernameApi(@PathVariable(name = "username") String username) {
        boolean isExists = accountService.isExistsUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of(isExists));
    }

    @GetMapping("/{code}")
    public ResponseEntity<ResponseFormat<String>> reissueTokenApi(@PathVariable(name = "code") String code, @RequestParam(name = "token") String refreshToken) {
        String accessToken = accountService.reissueAccessToken(code, refreshToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseFormat.of(accessToken));
    }

}

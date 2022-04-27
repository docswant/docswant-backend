package sju.capstone.docswant.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sju.capstone.docswant.common.format.ResponseFormat;
import sju.capstone.docswant.domain.member.service.account.AccountService;

@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
@RestController
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/exists")
    public ResponseEntity<ResponseFormat<Boolean>> existsUsernameApi(@RequestParam(name = "username") String username) {
        boolean isExists = accountService.isExistsUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of(isExists));
    }

}

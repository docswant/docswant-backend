package sju.capstone.docswant.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sju.capstone.docswant.common.annotation.CurrentUser;
import sju.capstone.docswant.common.format.ResponseFormat;
import sju.capstone.docswant.domain.member.model.dto.PatientDto;
import sju.capstone.docswant.domain.member.model.entity.Account;
import sju.capstone.docswant.domain.member.service.patient.PatientService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/v1/patient")
@RestController
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<ResponseFormat<PatientDto.Response>> registerApi(@Valid @RequestBody PatientDto.Request requestDto, @CurrentUser Account account) {
        PatientDto.Response responseDto = patientService.register(account, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseFormat.of(responseDto));
    }
}

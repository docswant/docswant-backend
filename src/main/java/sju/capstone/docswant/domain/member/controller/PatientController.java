package sju.capstone.docswant.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sju.capstone.docswant.common.annotation.CurrentUser;
import sju.capstone.docswant.common.format.PageFormat;
import sju.capstone.docswant.common.format.ResponseFormat;
import sju.capstone.docswant.domain.member.model.dto.PatientDto;
import sju.capstone.docswant.domain.member.model.entity.Account;
import sju.capstone.docswant.domain.member.service.patient.PatientService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/patient")
@RestController
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<ResponseFormat<PatientDto.Response>> registerApi(@RequestBody @Valid PatientDto.Request requestDto, @CurrentUser Account account) {
        PatientDto.Response responseDto = patientService.register(account, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseFormat.of(responseDto));
    }

    @PutMapping("/{code}")
    public ResponseEntity<ResponseFormat<PatientDto.Response>> updateApi(@PathVariable(name = "code") String code, @RequestBody @Valid PatientDto.Request requestDto) {
        PatientDto.Response responseDto = patientService.update(code, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of(responseDto));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<ResponseFormat> deleteApi(@PathVariable(name = "code") String code) {
        patientService.delete(code);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of());
    }

    @GetMapping("/{code}")
    public ResponseEntity<ResponseFormat<PatientDto.Response>> findApi(@PathVariable(name = "code") String code) {
        PatientDto.Response responseDto = patientService.find(code);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of(responseDto));
    }

    @GetMapping
    public ResponseEntity<ResponseFormat<PageFormat.Response>> findAllApi(PageFormat.Request pageRequest, @CurrentUser Account account) {
        PageFormat.Response<List<PatientDto.Response>> pageResponse = patientService.findAll(account, pageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of(pageResponse));
    }
}

package sju.capstone.docswant.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.time.LocalDate;
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

    @PatchMapping("/{code}")
    public ResponseEntity<ResponseFormat<PatientDto.Response>> updateApi(@PathVariable(name = "code") String code, @RequestBody @Valid PatientDto.UpdateRequest requestDto) {
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

    @GetMapping("/{code}/rounding")
    public ResponseEntity<ResponseFormat<PatientDto.PatientRoundingResponse>> findWithRoundingApi(
            @PathVariable(name = "code") String code, @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate today) {
        PatientDto.PatientRoundingResponse responseDto = patientService.findWithRounding(code, today);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of(responseDto));
    }

    @GetMapping
    public ResponseEntity<ResponseFormat<PageFormat.Response>> findAllApi(PageFormat.Request pageRequest, @CurrentUser Account account) {
        PageFormat.Response<List<PatientDto.ListResponse>> pageResponse = patientService.findAll(account, pageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of(pageResponse));
    }
}

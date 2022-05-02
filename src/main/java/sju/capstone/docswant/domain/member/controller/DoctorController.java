package sju.capstone.docswant.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sju.capstone.docswant.common.format.ResponseFormat;
import sju.capstone.docswant.domain.member.model.dto.DoctorDto;
import sju.capstone.docswant.domain.member.model.entity.doctor.CodeValidity;
import sju.capstone.docswant.domain.member.service.doctor.DoctorService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/v1/doctor")
@RestController
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping("/validate")
    public ResponseEntity<ResponseFormat<CodeValidity>> validateCodeApi(@RequestParam(name = "code") String code) {
        CodeValidity validity = doctorService.validateCode(code);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseFormat.of(validity));
    }

    @PostMapping
    public ResponseEntity<ResponseFormat<DoctorDto.Response>> registerApi(@Valid @RequestBody DoctorDto.Request requestDto) {
        DoctorDto.Response responseDto = doctorService.register(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseFormat.of(responseDto));
    }

}

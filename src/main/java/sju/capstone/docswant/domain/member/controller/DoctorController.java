package sju.capstone.docswant.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sju.capstone.docswant.common.dto.ResponseDto;
import sju.capstone.docswant.common.message.ResponseMessage;
import sju.capstone.docswant.domain.member.model.dto.DoctorDto;
import sju.capstone.docswant.domain.member.service.doctor.DoctorService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/v1/doctors")
@RestController
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<ResponseDto> registerApi(@Valid @RequestBody DoctorDto.Request requestDto) {
        DoctorDto.Response responseDto = doctorService.register(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(ResponseMessage.CREATE_SUCCESS, responseDto));
    }

}

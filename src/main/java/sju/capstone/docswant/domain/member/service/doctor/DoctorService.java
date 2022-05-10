package sju.capstone.docswant.domain.member.service.doctor;

import sju.capstone.docswant.domain.member.model.dto.DoctorDto;

public interface DoctorService {

    boolean isValidCode(String code);

    DoctorDto.Response register(DoctorDto.Request requestDto);

    DoctorDto.Response update(String code, DoctorDto.Request requestDto);

}

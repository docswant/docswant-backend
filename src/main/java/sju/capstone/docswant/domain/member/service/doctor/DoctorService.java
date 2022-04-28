package sju.capstone.docswant.domain.member.service.doctor;

import sju.capstone.docswant.domain.member.model.dto.DoctorDto;

public interface DoctorService {

    boolean isValidCode(String code);

    DoctorDto.Response register(DoctorDto.Request requestDTO);

    DoctorDto.Response update(DoctorDto.Request requestDTO);

}

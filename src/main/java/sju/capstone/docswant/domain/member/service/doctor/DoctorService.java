package sju.capstone.docswant.domain.member.service.doctor;

import sju.capstone.docswant.domain.member.model.dto.DoctorDto;
import sju.capstone.docswant.domain.member.model.entity.doctor.CodeValidity;

public interface DoctorService {

    CodeValidity validateCode(String code);

    DoctorDto.Response register(DoctorDto.Request requestDTO);

    DoctorDto.Response update(DoctorDto.Request requestDTO);

}

package sju.capstone.docswant.domain.member.service.doctor;

import sju.capstone.docswant.domain.member.model.dto.DoctorDto;
import sju.capstone.docswant.domain.member.model.entity.Account;

public interface DoctorService {

    boolean isValidCode(String code);

    DoctorDto.Response register(DoctorDto.Request requestDto);

    DoctorDto.Response update(Account account, DoctorDto.Request requestDto);

}

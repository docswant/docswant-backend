package sju.capstone.docswant.domain.member.service.patient;

import sju.capstone.docswant.domain.member.model.dto.PatientDto;
import sju.capstone.docswant.domain.member.model.entity.Account;

import java.util.List;

public interface PatientService {

    PatientDto.Response register(Account account, PatientDto.Request requestDto);

    PatientDto.Response update(PatientDto.Request requestDto);

    void delete(Account account, String code);

    PatientDto.Response find(Account account);

    List<PatientDto.Response> findAll(Account account);

}

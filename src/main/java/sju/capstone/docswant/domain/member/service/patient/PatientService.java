package sju.capstone.docswant.domain.member.service.patient;

import sju.capstone.docswant.common.format.PageFormat;
import sju.capstone.docswant.domain.member.model.dto.PatientDto;
import sju.capstone.docswant.domain.member.model.entity.Account;

import java.util.List;

public interface PatientService {

    PatientDto.Response register(Account account, PatientDto.Request requestDto);

    PatientDto.Response update(String code, PatientDto.Request requestDto);

    void delete(String code);

    PatientDto.Response find(String code);

    PageFormat.Response<List<PatientDto.Response>> findAll(Account account, PageFormat.Request pageRequest);

}

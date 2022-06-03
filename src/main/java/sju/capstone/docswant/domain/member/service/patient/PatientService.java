package sju.capstone.docswant.domain.member.service.patient;

import sju.capstone.docswant.common.format.PageFormat;
import sju.capstone.docswant.domain.member.model.dto.PatientDto;
import sju.capstone.docswant.domain.member.model.entity.Account;

import java.time.LocalDate;
import java.util.List;

public interface PatientService {

    PatientDto.Response register(Account account, PatientDto.Request requestDto);

    PatientDto.Response update(String code, PatientDto.UpdateRequest requestDto);

    void delete(String code);

    PatientDto.Response find(String code);

    PatientDto.PatientRoundingResponse findWithRounding(String code, LocalDate today);

    PageFormat.Response<List<PatientDto.ListResponse>> findAll(Account account, PageFormat.Request pageRequest);

}

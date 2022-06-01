package sju.capstone.docswant.domain.requirement.service;

import sju.capstone.docswant.common.format.PageFormat;
import sju.capstone.docswant.domain.member.model.entity.Account;
import sju.capstone.docswant.domain.requirement.model.dto.RequirementDto;

import java.util.List;

public interface RequirementService {
    RequirementDto.Response register(String code, RequirementDto.Request requestDto);

    RequirementDto.Response updateContent(Long id, RequirementDto.UpdateRequest requestDto);

    void delete(Long id);

    RequirementDto.Response find(Account account, Long id);

    PageFormat.Response<List<RequirementDto.Response>> findAll(String code, PageFormat.Request pageRequest);
}

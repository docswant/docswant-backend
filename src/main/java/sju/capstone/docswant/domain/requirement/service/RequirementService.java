package sju.capstone.docswant.domain.requirement.service;

import sju.capstone.docswant.common.format.PageFormat;
import sju.capstone.docswant.domain.requirement.model.dto.RequirementDto;

import java.util.List;

public interface RequirementService {
    RequirementDto.Response register(String code, RequirementDto.Request requestDto);

    RequirementDto.Response updateContent(Long id, RequirementDto.UpdateRequest requestDto);

    void delete(Long id);

    PageFormat.Response<List<RequirementDto.Response>> find(String code, PageFormat.Request pageRequest);
}

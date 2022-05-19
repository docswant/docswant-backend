package sju.capstone.docswant.domain.rounding.service;

import sju.capstone.docswant.domain.rounding.model.dto.RoundingDto;

import java.time.LocalDate;
import java.util.List;

public interface RoundingService {

    RoundingDto.Response create(String code, RoundingDto.Request requestDto);

    RoundingDto.Response update(Long id, RoundingDto.UpdateRequest requestDto);

    void delete(Long id);

    void deleteAll(List<Long> ids);

    RoundingDto.Response changeStatus(Long id);

    RoundingDto.Response find(Long id);

    List<RoundingDto.ListResponse> findAllByDate(String code, LocalDate roundingDate);

}

package sju.capstone.docswant.domain.question.service;

import sju.capstone.docswant.common.format.PageFormat;
import sju.capstone.docswant.domain.question.model.dto.QuestionDto;

import java.util.List;

public interface QuestionService {

    QuestionDto.Response create(String code, QuestionDto.Request requestDto);

    QuestionDto.Response update(Long id, QuestionDto.UpdateRequest requestDto);

    QuestionDto.Response answer(Long id, QuestionDto.AnswerRequest requestDto);

    void delete(Long id);

    QuestionDto.Response find(Long id);

    PageFormat.Response<List<QuestionDto.Response>> findAll(String code, PageFormat.Request pageRequest);

}

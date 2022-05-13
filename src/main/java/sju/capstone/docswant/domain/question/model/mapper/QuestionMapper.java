package sju.capstone.docswant.domain.question.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sju.capstone.docswant.domain.question.model.dto.QuestionDto;
import sju.capstone.docswant.domain.question.model.entity.Question;

@Mapper
public interface QuestionMapper {

    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    QuestionDto.Response toDto(Question question);

    Question toEntity(QuestionDto.Request requestDto);

}

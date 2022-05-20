package sju.capstone.docswant.domain.question.model.mapper;

import org.junit.jupiter.api.Test;
import sju.capstone.docswant.common.factory.DtoFactory;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.domain.question.model.dto.QuestionDto;
import sju.capstone.docswant.domain.question.model.entity.Question;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class QuestionMapperTest {

    private QuestionMapper mapper = QuestionMapper.INSTANCE;

    @Test
    void 엔티티에서_DTO_테스트() {
        //given
        Question requestEntity = EntityFactory.getQuestionEntity();

        //when
        QuestionDto.Response resultDto = mapper.toDto(requestEntity);

        //then
        assertThat(resultDto.getId()).isEqualTo(requestEntity.getId());
        assertThat(resultDto.getContent()).isEqualTo(requestEntity.getContent());
        assertThat(resultDto.getAnswer()).isEqualTo(requestEntity.getAnswer());
    }

    @Test
    void DTO에서_엔티티_테스트() {
        //given
        QuestionDto.Request requestDto = DtoFactory.getQuestionRequestDto();

        //when
        Question resultEntity = mapper.toEntity(requestDto);

        //then
        assertThat(resultEntity.getContent()).isEqualTo(requestDto.getContent());
    }
}
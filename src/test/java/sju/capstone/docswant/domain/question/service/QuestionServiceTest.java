package sju.capstone.docswant.domain.question.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sju.capstone.docswant.common.factory.DtoFactory;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.common.format.PageFormat;
import sju.capstone.docswant.domain.member.repository.patient.PatientRepository;
import sju.capstone.docswant.domain.question.model.dto.QuestionDto;
import sju.capstone.docswant.domain.question.model.entity.AnswerStatus;
import sju.capstone.docswant.domain.question.model.entity.Question;
import sju.capstone.docswant.domain.question.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Test
    void 질문_생성_테스트() {
        //given
        String code = "code";
        QuestionDto.Request requestDto = DtoFactory.getQuestionRequestDto();
        given(patientRepository.findByCode(any(String.class))).willReturn(Optional.of(EntityFactory.getPatientEntity()));

        //when
        QuestionDto.Response result = questionService.create(code, requestDto);

        //then
        assertThat(result.getContent()).isEqualTo(requestDto.getContent());
        assertThat(result.getAnswerStatus()).isEqualTo(AnswerStatus.TODO);
    }

    @Test
    void 질문_수정_테스트() {
        //given
        Long id = 1L;
        QuestionDto.UpdateRequest requestDto = DtoFactory.getQuestionUpdateRequestDto();
        given(questionRepository.findById(any(Long.class))).willReturn(Optional.of(EntityFactory.getQuestionEntity()));

        //when
        QuestionDto.Response result = questionService.update(id, requestDto);

        //then
        assertThat(result.getContent()).isEqualTo(requestDto.getContent());
    }

    @Test
    void 질문_응답_테스트() {
        //given
        Long id = 1L;
        QuestionDto.AnswerRequest requestDto = DtoFactory.getQuestionAnswerRequestDto();
        given(questionRepository.findById(any(Long.class))).willReturn(Optional.of(EntityFactory.getQuestionEntity()));

        //when
        QuestionDto.Response result = questionService.answer(id, requestDto);

        //then
        assertThat(result.getAnswer()).isEqualTo(requestDto.getAnswer());
        assertThat(result.getAnswerStatus()).isEqualTo(AnswerStatus.DONE);
    }

    @Test
    void 질문_조회_테스트() {
        //given
        Long id = 1L;
        Question question = EntityFactory.getQuestionEntity();
        given(questionRepository.findById(any(Long.class))).willReturn(Optional.of(question));

        //when
        QuestionDto.Response result = questionService.find(id);

        //then
        assertThat(result.getContent()).isEqualTo(question.getContent());
        assertThat(result.getAnswer()).isEqualTo(question.getAnswer());
    }

    @Test
    void 질문_리스트_조회_테스트() {
        //given
        String code = "code";
        PageFormat.Request pageRequest = new PageFormat.Request(1, 3);
        List<Question> questions = EntityFactory.getQuestionEntities();
        Page<Question> questionPage = new PageImpl<>(questions);
        given(questionRepository.findAllByPatientCode(any(String.class), any(Pageable.class))).willReturn(questionPage);

        //when
        PageFormat.Response<List<QuestionDto.Response>> pageResponse = questionService.findAll(code, pageRequest);

        //then
        assertThat(pageResponse.getPage()).isEqualTo(1);
        assertThat(pageResponse.getContent().size()).isEqualTo(questions.size());
        assertThat(pageResponse.getContent().get(0).getId()).isEqualTo(questions.get(0).getId());
    }
}
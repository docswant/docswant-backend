package sju.capstone.docswant.domain.question.model.entity;

import org.junit.jupiter.api.Test;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionTest {

    @Test
    void 환자_추가_테스트() {
        //given
        Question question = EntityFactory.getQuestionEntity();
        Patient patient = EntityFactory.getPatientEntity();

        //when
        question.setPatient(patient);

        //then
        assertThat(question.getPatient()).isEqualTo(patient);
    }

    @Test
    void 질문_수정_테스트() {
        //given
        Question question = EntityFactory.getQuestionEntity();
        String content = "update content";

        //when
        question.update(content);

        //then
        assertThat(question.getContent()).isEqualTo(content);
    }

    @Test
    void 질문_응답_테스트() {
        //given
        Question question = EntityFactory.getQuestionEntity();
        String answer = "answer";

        //when
        question.answer(answer);

        //then
        assertThat(question.getAnswer()).isEqualTo(answer);
    }
}

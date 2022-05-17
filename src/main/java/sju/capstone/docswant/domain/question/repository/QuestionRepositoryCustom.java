package sju.capstone.docswant.domain.question.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sju.capstone.docswant.domain.question.model.entity.Question;

public interface QuestionRepositoryCustom {

    Page<Question> findAllByPatientCode(String code, Pageable pageable);

}

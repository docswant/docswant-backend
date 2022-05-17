package sju.capstone.docswant.domain.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sju.capstone.docswant.domain.question.model.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositoryCustom {

}

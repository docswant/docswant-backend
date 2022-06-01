package sju.capstone.docswant.domain.question.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.question.model.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findAllByPatient(Patient patient, Pageable pageable);

}

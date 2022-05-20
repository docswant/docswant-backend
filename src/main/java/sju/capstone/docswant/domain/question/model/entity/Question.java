package sju.capstone.docswant.domain.question.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sju.capstone.docswant.common.entity.BaseTimeEntity;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "question")
@Entity
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Column(name = "question_content", nullable = false, length = 255)
    private String content;

    @Column(name = "question_answer", length = 100)
    private String answer;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_answer_status", nullable = false)
    private AnswerStatus answerStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_code")
    private Patient patient;

    @Builder
    public Question(String content) {
        this.content = content;
        this.answerStatus = AnswerStatus.TODO;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void update(String content) {
        if (content != null) {
            this.content = content;
        }
    }

    public void answer(String answer) {
        this.answer = answer;
        this.answerStatus = AnswerStatus.DONE;
    }
}

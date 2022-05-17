package sju.capstone.docswant.domain.question.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sju.capstone.docswant.domain.question.model.entity.Question;

import java.util.List;

import static sju.capstone.docswant.domain.question.model.entity.QQuestion.question;

@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Question> findAllByPatientCode(String code, Pageable pageable) {
        List<Question> result = queryFactory
                .selectFrom(question)
                .where(question.patient.code.eq(code))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(result, pageable, result.size());
    }
}

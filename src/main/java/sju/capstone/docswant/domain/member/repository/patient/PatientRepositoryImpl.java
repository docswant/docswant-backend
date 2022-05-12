package sju.capstone.docswant.domain.member.repository.patient;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;

import java.util.List;

import static sju.capstone.docswant.domain.member.model.entity.patient.QPatient.patient;

@RequiredArgsConstructor
public class PatientRepositoryImpl implements PatientRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Patient> findAllByDoctorCode(String code, Pageable pageable) {
        List<Patient> result = queryFactory
                .selectFrom(patient)
                .where(patient.doctor.code.eq(code))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(result, pageable, result.size());
    }
}

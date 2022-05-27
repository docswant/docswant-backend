package sju.capstone.docswant.domain.rounding.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.rounding.model.entity.Rounding;

import java.time.LocalDate;
import java.util.List;

import static sju.capstone.docswant.domain.rounding.model.entity.QRounding.rounding;

@RequiredArgsConstructor
public class RoundingRepositoryImpl implements RoundingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Rounding> findAllByDoctorAndRoundingDateOrderByRoundingTimeAsc(Doctor doctor, LocalDate roundingDate) {
        return queryFactory
                .selectFrom(rounding)
                .join(rounding.patient)
                .fetchJoin()
                .where(rounding.doctor.eq(doctor).and(rounding.roundingSchedule.roundingDate.eq(roundingDate)))
                .orderBy(rounding.roundingSchedule.roundingTime.asc())
                .fetch();
    }
}

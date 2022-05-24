package sju.capstone.docswant.domain.rounding.repository;

import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.rounding.model.entity.Rounding;

import java.time.LocalDate;
import java.util.List;

public interface RoundingRepositoryCustom {

    List<Rounding> findAllByDoctorAndRoundingDateOrderByRoundingTimeAsc(Doctor doctor, LocalDate roundingDate);

}

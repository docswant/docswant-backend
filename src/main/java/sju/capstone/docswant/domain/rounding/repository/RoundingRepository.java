package sju.capstone.docswant.domain.rounding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.rounding.model.entity.Rounding;

import java.time.LocalDate;

public interface RoundingRepository extends JpaRepository<Rounding, Long>, RoundingRepositoryCustom {

    Rounding findByPatientAndRoundingScheduleRoundingDate(Patient patient, LocalDate roundingDate);

}

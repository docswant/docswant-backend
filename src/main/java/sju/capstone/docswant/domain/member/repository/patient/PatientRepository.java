package sju.capstone.docswant.domain.member.repository.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, String>, PatientRepositoryCustom {

    Optional<Patient> findByCode(String code);

    void deleteByCode(String code);

}

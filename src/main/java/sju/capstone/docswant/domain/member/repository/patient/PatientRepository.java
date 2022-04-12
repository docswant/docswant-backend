package sju.capstone.docswant.domain.member.repository.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import sju.capstone.docswant.domain.member.entity.patient.Patient;

public interface PatientRepository extends JpaRepository<Patient, String> {

}

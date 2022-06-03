package sju.capstone.docswant.domain.requirement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.requirement.model.entity.Requirement;

import java.util.List;
import java.util.Optional;

public interface RequirementRepository extends JpaRepository<Requirement, Long> {

        Optional<Requirement> findById(Long id);

        Page<Requirement> findAllByPatientCode(String code, Pageable pageable);

        List<Requirement> findAllByPatient(Patient patient);

}

package sju.capstone.docswant.domain.member.repository.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;

public interface PatientRepositoryCustom {

    Page<Patient> findAllByDoctorCode(String code, Pageable pageable);

}

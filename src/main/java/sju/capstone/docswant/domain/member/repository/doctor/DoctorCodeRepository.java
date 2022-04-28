package sju.capstone.docswant.domain.member.repository.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import sju.capstone.docswant.domain.member.model.entity.doctor.DoctorCode;

public interface DoctorCodeRepository extends JpaRepository<DoctorCode, String> {

}

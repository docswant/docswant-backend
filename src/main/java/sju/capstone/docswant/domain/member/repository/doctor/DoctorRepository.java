package sju.capstone.docswant.domain.member.repository.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, String> {

}

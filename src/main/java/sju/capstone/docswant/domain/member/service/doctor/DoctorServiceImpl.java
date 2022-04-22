package sju.capstone.docswant.domain.member.service.doctor;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sju.capstone.docswant.domain.member.model.dto.DoctorDto;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.member.model.mapper.DoctorMapper;
import sju.capstone.docswant.domain.member.repository.doctor.DoctorRepository;

@RequiredArgsConstructor
@Service
public class DoctorServiceImpl implements DoctorService{

    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final DoctorMapper mapper;

    @Transactional
    @Override
    public DoctorDto.Response register(DoctorDto.Request requestDto) {
        Doctor doctor = mapper.toEntity(requestDto);
        String encodedPassword = passwordEncoder.encode(doctor.getPassword());
        doctor.setEncodedPassword(encodedPassword);
        doctorRepository.save(doctor);
        return mapper.toDto(doctor);
    }

    @Override
    public DoctorDto.Response update(DoctorDto.Request requestDTO) {
        return null;
    }

}

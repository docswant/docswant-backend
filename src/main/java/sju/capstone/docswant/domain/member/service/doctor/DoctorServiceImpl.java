package sju.capstone.docswant.domain.member.service.doctor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sju.capstone.docswant.common.annotation.DoctorOnly;
import sju.capstone.docswant.core.error.ErrorCode;
import sju.capstone.docswant.core.error.exception.EntityNotFoundException;
import sju.capstone.docswant.domain.member.model.dto.DoctorDto;
import sju.capstone.docswant.domain.member.model.entity.Account;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.member.model.mapper.DoctorMapper;
import sju.capstone.docswant.domain.member.repository.doctor.DoctorCodeRepository;
import sju.capstone.docswant.domain.member.repository.doctor.DoctorRepository;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DoctorServiceImpl implements DoctorService{

    private final DoctorRepository doctorRepository;
    private final DoctorCodeRepository doctorCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final DoctorMapper mapper = DoctorMapper.INSTANCE;

    @Transactional(readOnly = true)
    @Override
    public boolean isValidCode(String code) {
        log.info("validate doctor code. code = {}", code);
        return doctorCodeRepository.existsByCode(code);
    }

    @Transactional
    @Override
    public DoctorDto.Response register(DoctorDto.Request requestDto) {
        Doctor doctor = mapper.toEntity(requestDto);
        String encodedPassword = passwordEncoder.encode(doctor.getPassword());
        doctor.updateAccount(requestDto.getUsername(), encodedPassword);
        doctorRepository.save(doctor);
        log.info("register success. code = {}", doctor.getCode());
        return mapper.toDto(doctor);
    }

    @DoctorOnly
    @Transactional
    @Override
    public DoctorDto.Response update(String code, DoctorDto.Request requestDto) {
        Doctor doctor = doctorRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        if (requestDto.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
            doctor.update(requestDto.getUsername(), encodedPassword);
        } else {
            doctor.update(requestDto.getUsername(), requestDto.getPassword());
        }
        log.info("update success. code = {}", doctor.getCode());
        return mapper.toDto(doctor);
    }

}

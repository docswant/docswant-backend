package sju.capstone.docswant.domain.member.service.patient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sju.capstone.docswant.common.annotation.DoctorOnly;
import sju.capstone.docswant.core.error.ErrorCode;
import sju.capstone.docswant.core.error.exception.EntityNotFoundException;
import sju.capstone.docswant.domain.member.model.dto.PatientDto;
import sju.capstone.docswant.domain.member.model.entity.Account;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.member.model.mapper.PatientMapper;
import sju.capstone.docswant.domain.member.repository.doctor.DoctorRepository;
import sju.capstone.docswant.domain.member.repository.patient.PatientRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientMapper mapper = PatientMapper.INSTANCE;

    @DoctorOnly
    @Transactional
    @Override
    public PatientDto.Response register(Account account, PatientDto.Request requestDto) {
        Doctor doctor = doctorRepository.findByCode(account.getCode()).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        Patient patient = mapper.toEntity(requestDto);
        generateDefaultAccount(patient);
        doctor.addPatient(patient);
        patient.setDoctor(doctor);
        patientRepository.save(patient);
        log.info("register success. code = {}", patient.getCode());
        return mapper.toDto(patient);
    }

    @Override
    public PatientDto.Response update(PatientDto.Request requestDto) {
        return null;
    }

    @Override
    public void delete(Account account, String code) {

    }

    @Override
    public PatientDto.Response find(Account account) {
        return null;
    }

    @Override
    public List<PatientDto.Response> findAll(Account account) {
        return null;
    }

    private void generateDefaultAccount(Patient patient) {
        String defaultUsername = patient.getCode();
        String defaultPassword = passwordEncoder.encode(removeHyphenFromDate(patient.getBirthDate()));
        patient.updateAccount(defaultUsername, defaultPassword);
    }

    private String removeHyphenFromDate(LocalDate date) {
        return Arrays.stream(date.toString().split("-")).collect(Collectors.joining());
    }

}

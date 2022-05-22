package sju.capstone.docswant.domain.member.service.patient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sju.capstone.docswant.common.annotation.DoctorOnly;
import sju.capstone.docswant.common.annotation.PatientOnly;
import sju.capstone.docswant.common.format.PageFormat;
import sju.capstone.docswant.core.error.ErrorCode;
import sju.capstone.docswant.core.error.exception.EntityNotFoundException;
import sju.capstone.docswant.domain.member.model.dto.PatientDto;
import sju.capstone.docswant.domain.member.model.entity.Account;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.member.model.mapper.PatientMapper;
import sju.capstone.docswant.domain.member.repository.doctor.DoctorRepository;
import sju.capstone.docswant.domain.member.repository.patient.PatientRepository;
import sju.capstone.docswant.domain.rounding.model.entity.Rounding;
import sju.capstone.docswant.domain.rounding.repository.RoundingRepository;

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
    private final RoundingRepository roundingRepository;
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

    @Transactional
    @Override
    public PatientDto.Response update(String code, PatientDto.UpdateRequest requestDto) {
        Patient patient = patientRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        if (requestDto.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
            patient.update(requestDto.getUsername(), encodedPassword, requestDto.getName(), requestDto.getBirthDate(), requestDto.getHospitalizationDate(),
                    requestDto.getSurgeryDate(), requestDto.getDischargeDate(), requestDto.getDiseaseName(), requestDto.getHospitalRoom());
        } else {
            patient.update(requestDto.getUsername(), requestDto.getPassword(), requestDto.getName(), requestDto.getBirthDate(), requestDto.getHospitalizationDate(),
                    requestDto.getSurgeryDate(), requestDto.getDischargeDate(), requestDto.getDiseaseName(), requestDto.getHospitalRoom());
        }
        log.info("update success. code = {}", patient.getCode());
        return mapper.toDto(patient);
    }

    @DoctorOnly
    @Transactional
    @Override
    public void delete(String code) {
        patientRepository.deleteByCode(code);
        log.info("delete success. code = {}", code);
        return;
    }

    @Transactional(readOnly = true)
    @Override
    public PatientDto.Response find(String code) {
        Patient patient = patientRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        log.info("patient find success. code = {}", patient.getCode());
        return mapper.toDto(patient);
    }

    @PatientOnly
    @Transactional(readOnly = true)
    @Override
    public PatientDto.PatientRoundingResponse findWithRounding(String code, LocalDate today) {
        Patient patient = patientRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        Rounding rounding = roundingRepository.findByPatientAndRoundingScheduleRoundingDate(patient, today);
        log.info("patient find with rounding success. code = {}, date = [}", patient.getCode(), today);
        return mapper.toPatientRoundingDto(patient, rounding);
    }

    @DoctorOnly
    @Transactional(readOnly = true)
    @Override
    public PageFormat.Response<List<PatientDto.Response>> findAll(Account account, PageFormat.Request pageRequest) {
        Doctor doctor = (Doctor) account;
        Page<Patient> patientPage = patientRepository.findAllByDoctorCode(doctor.getCode(), pageRequest.of());
        List<PatientDto.Response> responseDtos = patientPage.getContent().stream().map(mapper::toDto).collect(Collectors.toList());
        return PageFormat.Response.of(patientPage.getNumber(), patientPage.hasNext(), responseDtos);
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

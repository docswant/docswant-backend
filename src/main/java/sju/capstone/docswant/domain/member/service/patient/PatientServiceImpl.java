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
import sju.capstone.docswant.domain.requirement.model.entity.Requirement;
import sju.capstone.docswant.domain.requirement.model.entity.RequirementStatus;
import sju.capstone.docswant.domain.requirement.repository.RequirementRepository;
import sju.capstone.docswant.domain.rounding.model.entity.Rounding;
import sju.capstone.docswant.domain.rounding.model.entity.RoundingStatus;
import sju.capstone.docswant.domain.rounding.repository.RoundingRepository;
import sju.capstone.docswant.infra.esl.EslClient;
import sju.capstone.docswant.infra.esl.EslDto;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final RoundingRepository roundingRepository;
    private final RequirementRepository requirementRepository;
    private final PasswordEncoder passwordEncoder;
    private final EslClient eslClient;
    private final PatientMapper mapper = PatientMapper.INSTANCE;

    @DoctorOnly
    @Transactional
    @Override
    public PatientDto.Response register(Account account, PatientDto.Request requestDto) {
        Doctor doctor = doctorRepository.findByCode(account.getCode()).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        Patient patient = mapper.toEntity(requestDto);
        generateDefaultAccount(patient);
        doctor.addPatient(patient);
        patientRepository.save(patient);
        log.info("patient register success. code = {}", patient.getCode());
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
        log.info("patient update success. code = {}", patient.getCode());
        return mapper.toDto(patient);
    }

    @DoctorOnly
    @Transactional
    @Override
    public void delete(String code) {
        patientRepository.deleteByCode(code);
        log.info("patient delete success. code = {}", code);
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
        List<Rounding> todayRoundings = roundingRepository.findAllByDoctorAndRoundingDateOrderByRoundingTimeAsc(patient.getDoctor(), today);
        Rounding roundingForPatient = todayRoundings.stream().filter(todayRounding -> todayRounding.getPatient().equals(patient)).findFirst().orElse(null);
        Integer roundsWaitingOrder;
        if (roundingForPatient != null) {
            if (roundingForPatient.getRoundingStatus().equals(RoundingStatus.TODO)) {
                List<Rounding> todoTodayRoundings = todayRoundings.stream().filter(todayRounding -> todayRounding.getRoundingStatus().equals(RoundingStatus.TODO)).collect(Collectors.toList());
                roundsWaitingOrder = todoTodayRoundings.indexOf(roundingForPatient);
            } else {
                roundsWaitingOrder = 0;
            }
        } else {
            roundsWaitingOrder = null;
        }
        log.info("patient find with rounding success. code = {}, date = {}", patient.getCode(), today);
        EslDto.ChangeRequest changeRequestDto = EslDto.ChangeRequest.toDto(patient, roundingForPatient, roundsWaitingOrder);
        eslClient.sendChangeRequest(changeRequestDto);
        return mapper.toPatientRoundingDto(patient, roundingForPatient, roundsWaitingOrder);
    }

    @DoctorOnly
    @Transactional(readOnly = true)
    @Override
    public PageFormat.Response<List<PatientDto.ListResponse>> findAll(Account account, PageFormat.Request pageRequest) {
        Doctor doctor = doctorRepository.findByCode(account.getCode()).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        Page<Patient> patientPage = patientRepository.findAllByDoctor(doctor, pageRequest.of());
        List<PatientDto.ListResponse> responseDtos = new ArrayList<>();
        patientPage.getContent().forEach(patient -> {
            List<Requirement> requirements = requirementRepository.findAllByPatient(patient);
            Optional<Requirement> unreadRequirement = requirements.stream().filter(requirement -> requirement.getStatus().equals(RequirementStatus.UNREAD)).findFirst();
            Boolean hasUnreadRequirement = unreadRequirement.isPresent() ? true : false;
            PatientDto.ListResponse listDto = mapper.toListDto(patient, hasUnreadRequirement);
            responseDtos.add(listDto);
        });
        log.info("patient find all success. page = {}, size = {}", patientPage.getNumber(), patientPage.getNumberOfElements());
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

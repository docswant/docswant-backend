package sju.capstone.docswant.domain.rounding.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sju.capstone.docswant.common.annotation.DoctorOnly;
import sju.capstone.docswant.core.error.ErrorCode;
import sju.capstone.docswant.core.error.exception.EntityNotFoundException;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.member.repository.doctor.DoctorRepository;
import sju.capstone.docswant.domain.member.repository.patient.PatientRepository;
import sju.capstone.docswant.domain.rounding.model.dto.RoundingDto;
import sju.capstone.docswant.domain.rounding.model.entity.Rounding;
import sju.capstone.docswant.domain.rounding.model.entity.RoundingStatus;
import sju.capstone.docswant.domain.rounding.model.mapper.RoundingMapper;
import sju.capstone.docswant.domain.rounding.repository.RoundingRepository;
import sju.capstone.docswant.infra.esl.EslClient;
import sju.capstone.docswant.infra.esl.EslDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoundingServiceImpl implements RoundingService {

    private final RoundingRepository roundingRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final EslClient eslClient;
    private final RoundingMapper mapper = RoundingMapper.INSTANCE;

    @DoctorOnly
    @Transactional
    @Override
    public RoundingDto.Response create(String code, RoundingDto.Request requestDto) {
        Doctor doctor = doctorRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        Patient patient = patientRepository.findByCode(requestDto.getCode()).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        Rounding rounding = mapper.toEntity(requestDto);
        doctor.addRounding(rounding);
        patient.addRounding(rounding);
        roundingRepository.save(rounding);
        log.info("rounding save success. id = {}", rounding.getId());
        return mapper.toDto(rounding);
    }

    @DoctorOnly
    @Transactional
    @Override
    public RoundingDto.Response update(Long id, RoundingDto.UpdateRequest requestDto) {
        Rounding rounding = roundingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        rounding.update(requestDto.getRoundingDate(), requestDto.getRoundingTime());
        log.info("rounding update success. id = {}", rounding.getId());
        return mapper.toDto(rounding);
    }

    @DoctorOnly
    @Transactional
    @Override
    public void delete(Long id) {
        roundingRepository.deleteById(id);
        log.info("rounding delete success. id = {}", id);
        return;
    }

    @DoctorOnly
    @Transactional
    @Override
    public void deleteAll(List<Long> ids) {
        roundingRepository.deleteAllById(ids);
        log.info("rounding delete all success.");
        return;
    }

    @DoctorOnly
    @Transactional
    @Override
    public RoundingDto.Response changeStatus(Long id) {
        Rounding rounding = roundingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        rounding.changeStatus();
        log.info("rounding change status success. id ={}", rounding.getId());
        return mapper.toDto(rounding);
    }

    @Transactional(readOnly = true)
    @Override
    public RoundingDto.Response find(Long id) {
        Rounding rounding = roundingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        log.info("rounding find success. id = {}", rounding.getId());
        return mapper.toDto(rounding);
    }

    @DoctorOnly
    @Transactional(readOnly = true)
    @Override
    public List<RoundingDto.ListResponse> findAllByDate(String code, LocalDate date) {
        Doctor doctor = doctorRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        List<Rounding> roundings = roundingRepository.findAllByDoctorAndRoundingDateOrderByRoundingTimeAsc(doctor, date);
        log.info("rounding find all success. doctor code = {}, rounding date = {}", code, date);
        if (LocalDate.now().isEqual(date)) {
            List<Rounding> todoTodayRoundings = roundings.stream().filter(rounding -> rounding.getRoundingStatus().equals(RoundingStatus.TODO)).collect(Collectors.toList());
            todoTodayRoundings.forEach(rounding -> {
                Integer roundsWaitingOrder = todoTodayRoundings.indexOf(rounding);
                EslDto.ChangeRequest changeRequestDto = EslDto.ChangeRequest.toDto(rounding.getPatient(), rounding, roundsWaitingOrder);
                eslClient.sendChangeRequest(changeRequestDto);
            });
        }
        return mapper.toListDto(roundings);
    }
}

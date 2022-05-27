package sju.capstone.docswant.domain.requirement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sju.capstone.docswant.common.annotation.PatientOnly;
import sju.capstone.docswant.common.format.PageFormat;
import sju.capstone.docswant.core.error.ErrorCode;
import sju.capstone.docswant.core.error.exception.EntityNotFoundException;
import sju.capstone.docswant.domain.member.model.entity.Account;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.member.repository.patient.PatientRepository;
import sju.capstone.docswant.domain.requirement.model.dto.RequirementDto;
import sju.capstone.docswant.domain.requirement.model.entity.Requirement;
import sju.capstone.docswant.domain.requirement.model.mapper.RequirementMapper;
import sju.capstone.docswant.domain.requirement.repository.RequirementRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RequirementServiceImpl implements RequirementService {

    private final RequirementRepository requirementRepository;
    private final PatientRepository patientRepository;
    private final RequirementMapper mapper = RequirementMapper.INSTANCE;

    @Transactional
    @Override
    public RequirementDto.Response register(String code, RequirementDto.Request requestDto){
        Patient patient = patientRepository.findByCode(code).orElseThrow(()-> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        Requirement requirement = mapper.toEntity(requestDto);
        requirement.setPatient(patient);
        patient.addRequirement(requirement);
        requirementRepository.save(requirement);
        log.info("register success. id = {}", requirement.getId());
        return mapper.toDto(requirement);
    }

    @Transactional
    @Override
    public RequirementDto.Response updateContent(Long id, RequirementDto.UpdateRequest requestDto){
        Requirement requirement = requirementRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        requirement.updateContent(requirement, requestDto.getContent());
        log.info("update success. id = {}", requirement.getId());
        return mapper.toDto(requirement);
    }

    @PatientOnly
    @Transactional
    @Override
    public void delete(Long id) {
        requirementRepository.deleteById(id);
        log.info("delete success. id = {}", id);
        return;
    }

    @Transactional(readOnly = true)
    @Override
    public RequirementDto.Response find(Account account, Long id) {
        Requirement requirement = requirementRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        if ("ACCOUNT_DOCTOR".equals(account.getAccountType())) {
            requirement.changeStatusToRead();
        }
        return mapper.toDto(requirement);
    }

    @Transactional
    @Override
    public PageFormat.Response<List<RequirementDto.Response>>findAll(String code, PageFormat.Request pageRequest) {
        Page<Requirement> requirementPage = requirementRepository.findAllByPatientCode(code, pageRequest.of());
        List<RequirementDto.Response> responseDtos = requirementPage.getContent().stream().map(mapper::toDto).collect(Collectors.toList());
        log.info("find all success. page = {}, size = {}", requirementPage.getNumber(), requirementPage.getNumberOfElements());
        return PageFormat.Response.of(requirementPage.getNumber(), requirementPage.hasNext(), responseDtos);
    }
}


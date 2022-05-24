package sju.capstone.docswant.domain.question.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sju.capstone.docswant.common.annotation.DoctorOnly;
import sju.capstone.docswant.common.annotation.PatientOnly;
import sju.capstone.docswant.common.format.PageFormat;
import sju.capstone.docswant.core.error.ErrorCode;
import sju.capstone.docswant.core.error.exception.EntityNotFoundException;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.member.repository.patient.PatientRepository;
import sju.capstone.docswant.domain.question.model.dto.QuestionDto;
import sju.capstone.docswant.domain.question.model.entity.Question;
import sju.capstone.docswant.domain.question.model.mapper.QuestionMapper;
import sju.capstone.docswant.domain.question.repository.QuestionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final PatientRepository patientRepository;
    private final QuestionMapper mapper = QuestionMapper.INSTANCE;

    @DoctorOnly
    @Transactional
    @Override
    public QuestionDto.Response create(String code, QuestionDto.Request requestDto) {
        Patient patient = patientRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        Question question = mapper.toEntity(requestDto);
        question.setPatient(patient);
        patient.addQuestion(question);
        questionRepository.save(question);
        log.info("question create success. id = {}", question.getId());
        return mapper.toDto(question);
    }

    @DoctorOnly
    @Transactional
    @Override
    public QuestionDto.Response update(Long id, QuestionDto.UpdateRequest requestDto) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        question.update(requestDto.getContent());
        log.info("question update success. id = {}", question.getId());
        return mapper.toDto(question);
    }

    @PatientOnly
    @Transactional
    @Override
    public QuestionDto.Response answer(Long id, QuestionDto.AnswerRequest requestDto) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        question.answer(requestDto.getAnswer());
        log.info("question answer success. id = {}", question.getId());
        return mapper.toDto(question);
    }

    @DoctorOnly
    @Transactional
    @Override
    public void delete(Long id) {
        questionRepository.deleteById(id);
        log.info("question delete success. id = {}", id);
        return;
    }

    @Transactional(readOnly = true)
    @Override
    public QuestionDto.Response find(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        log.info("question find success. id = {}", question.getId());
        return mapper.toDto(question);
    }

    @Transactional(readOnly = true)
    @Override
    public PageFormat.Response<List<QuestionDto.Response>> findAll(String code, PageFormat.Request pageRequest) {
        Patient patient = patientRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        Page<Question> questionPage = questionRepository.findAllByPatient(patient, pageRequest.of());
        List<QuestionDto.Response> responseDtos = questionPage.getContent().stream().map(mapper::toDto).collect(Collectors.toList());
        log.info("question find all success. page = {}, size = {}", questionPage.getNumber(), questionPage.getNumberOfElements());
        return PageFormat.Response.of(questionPage.getNumber(), questionPage.hasNext(), responseDtos);
    }
}

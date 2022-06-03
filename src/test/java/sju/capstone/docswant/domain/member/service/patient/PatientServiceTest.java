package sju.capstone.docswant.domain.member.service.patient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import sju.capstone.docswant.common.factory.DtoFactory;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.common.format.PageFormat;
import sju.capstone.docswant.domain.member.model.dto.PatientDto;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.member.repository.doctor.DoctorRepository;
import sju.capstone.docswant.domain.member.repository.patient.PatientRepository;
import sju.capstone.docswant.domain.requirement.model.entity.Requirement;
import sju.capstone.docswant.domain.requirement.model.entity.RequirementStatus;
import sju.capstone.docswant.domain.requirement.repository.RequirementRepository;
import sju.capstone.docswant.domain.rounding.model.entity.Rounding;
import sju.capstone.docswant.domain.rounding.repository.RoundingRepository;
import sju.capstone.docswant.infra.esl.EslClient;

import javax.print.Doc;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;
    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private RoundingRepository roundingRepository;
    @Mock
    private RequirementRepository requirementRepository;
    @Mock
    private EslClient eslClient;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Test
    void 환자_등록_테스트() {
        //given
        PatientDto.Request requestDto = DtoFactory.getPatientRequestDto();
        Doctor doctor = EntityFactory.getDoctorEntity();
        given(doctorRepository.findByCode(any())).willReturn(Optional.of(doctor));

        //when
        PatientDto.Response result = patientService.register(doctor, requestDto);

        //then
        assertThat(result.getCode()).isEqualTo(requestDto.getCode());
    }

    @Test
    void 환자_정보수정_테스트() {
        //given
        Patient patient = EntityFactory.getPatientEntity();
        PatientDto.UpdateRequest requestDto = DtoFactory.getPatientUpdateRequestDto();
        given(patientRepository.findByCode(any(String.class))).willReturn(Optional.of(patient));

        //when
        PatientDto.Response result = patientService.update(patient.getCode(), requestDto);

        //then
        assertThat(result.getName()).isEqualTo(requestDto.getName());
        assertThat(result.getHospitalizationDate()).isEqualTo(requestDto.getHospitalizationDate());
    }

    @Test
    void 환자_조회_테스트() {
        //given
        Patient patient = EntityFactory.getPatientEntity();
        given(patientRepository.findByCode(any(String.class))).willReturn(Optional.of(patient));

        //when
        PatientDto.Response responseDto = patientService.find(patient.getCode());

        //then
        assertThat(responseDto.getCode()).isEqualTo(patient.getCode());
        assertThat(responseDto.getName()).isEqualTo(patient.getName());
    }

    @Test
    void 환자_회진_조회_테스트() {
        //given
        String code = "code";
        LocalDate today = LocalDate.of(2022, 5, 7);
        List<Rounding> roundings = EntityFactory.getRoundingEntities();
        Rounding roundingForPatient = roundings.get(0);
        Patient patient = roundings.get(0).getPatient();
        Doctor doctor = roundings.get(0).getDoctor();
        given(patientRepository.findByCode(any(String.class))).willReturn(Optional.of(patient));
        given(roundingRepository.findAllByDoctorAndRoundingDateOrderByRoundingTimeAsc(any(Doctor.class), any(LocalDate.class))).willReturn(roundings);

        //when
        PatientDto.PatientRoundingResponse responseDto = patientService.findWithRounding(code, today);

        //then
        assertThat(responseDto.getPatientName()).isEqualTo(patient.getName());
        assertThat(responseDto.getDoctorName()).isEqualTo(doctor.getName());
        assertThat(responseDto.getRoundingTime()).isEqualTo(roundingForPatient.getRoundingSchedule().getRoundingTime());
        assertThat(responseDto.getRoundsWaitingOrder()).isEqualTo(roundings.indexOf(roundingForPatient));
    }

    @Test
    void 환자_리스트_조회_테스트() {
        //given
        Doctor doctor = EntityFactory.getDoctorEntity();
        PageFormat.Request pageRequest = new PageFormat.Request(1, 3);
        List<Patient> patients = EntityFactory.getPatientEntities();
        Page<Patient> patientPage = new PageImpl<>(patients);
        Requirement requirement = EntityFactory.getRequirementEntity();
        given(doctorRepository.findByCode(any(String.class))).willReturn(Optional.of(EntityFactory.getDoctorEntity()));
        given(patientRepository.findAllByDoctor(any(Doctor.class), any(Pageable.class))).willReturn(patientPage);
        given(requirementRepository.findAllByPatient(any(Patient.class))).willReturn(Arrays.asList(requirement));

        //when
        PageFormat.Response<List<PatientDto.ListResponse>> pageResponse = patientService.findAll(doctor, pageRequest);

        //then
        assertThat(pageResponse.getPage()).isEqualTo(1);
        assertThat(pageResponse.getContent().size()).isEqualTo(patients.size());
        assertThat(pageResponse.getContent().get(0).getCode()).isEqualTo(patients.get(0).getCode());
        assertThat(pageResponse.getContent().get(0).getHasUnreadRequirement()).isEqualTo(requirement.getStatus().equals(RequirementStatus.UNREAD));
    }

}
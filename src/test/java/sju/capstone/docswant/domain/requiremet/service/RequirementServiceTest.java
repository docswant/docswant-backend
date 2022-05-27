package sju.capstone.docswant.domain.requiremet.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.common.format.PageFormat;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.member.repository.patient.PatientRepository;
import sju.capstone.docswant.domain.requirement.model.dto.RequirementDto;
import sju.capstone.docswant.domain.requirement.model.entity.Requirement;
import sju.capstone.docswant.domain.requirement.model.entity.RequirementStatus;
import sju.capstone.docswant.domain.requirement.repository.RequirementRepository;
import sju.capstone.docswant.domain.requirement.service.RequirementServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RequirementServiceTest {

    @Mock
    private RequirementRepository requirementRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private RequirementServiceImpl requirementService;


    @Test
    void 문의사항_등록_테스트() {
        //given
        String code="code";
        Patient patient = Patient.builder().code("PATIENT001").build();
        RequirementDto.Request requestDto = RequirementDto.Request.builder().content("문의사항입니다.").build();

        given(patientRepository.findByCode(any())).willReturn(Optional.of(patient));

        //when
        RequirementDto.Response result = requirementService.register(code, requestDto);

        //then
        assertThat(result.getContent()).isEqualTo(requestDto.getContent());
        assertThat(result.getStatus()).isEqualTo(RequirementStatus.UNREAD);
    }

    @Test
    void 문의사항_내용수정_테스트(){//id = NULL 부여가 안됨
        //given
        Long id = 1L;
        RequirementDto.UpdateRequest requestDto = RequirementDto.UpdateRequest.builder().content("수정된 문의사항입니다.").build();
        Requirement requirement = Requirement.builder().content("문의사항입니다.").build();
        given(requirementRepository.findById(any(Long.class))).willReturn(Optional.of(requirement));

        //when
        RequirementDto.Response result = requirementService.updateContent(id, requestDto);

        //then
        assertThat(result.getContent()).isEqualTo(requestDto.getContent());
    }

    @Test
    void 문의사항_조회_테스트(){
        //given
        Requirement requirement = Requirement.builder().content("문의사항입니다.").build();
        Doctor doctor = EntityFactory.getDoctorEntity();
        given(requirementRepository.findById(any(Long.class))).willReturn(Optional.of(requirement));

        //when
        RequirementDto.Response responseDto = requirementService.find(doctor, 1L);

        //then
        assertThat(responseDto.getContent()).isEqualTo(requirement.getContent());
    }

    @Test
    void 환자_문의사항_전체_조회_테스트(){
        //given
        String code="code";

        PageFormat.Request pageRequest = new PageFormat.Request(1, 3);

        Requirement requirement1 = Requirement.builder().content("문의사항1").build();
        Requirement requirement2 = Requirement.builder().content("문의사항2").build();
        Requirement requirement3 = Requirement.builder().content("문의사항3").build();

        List<Requirement> requirements = Arrays.asList(requirement1, requirement2, requirement3);

        Page<Requirement> requirementPage = new PageImpl<>(requirements);
        given(requirementRepository.findAllByPatientCode(any(String.class), any(Pageable.class))).willReturn(requirementPage);

        //when
        PageFormat.Response<List<RequirementDto.Response>> pageResponse = requirementService.findAll(code, pageRequest);

        //then
        assertThat(pageResponse.getPage()).isEqualTo(1);
        assertThat(pageResponse.getContent().size()).isEqualTo(requirements.size());
        assertThat(pageResponse.getContent().get(0).getId()).isEqualTo(requirements.get(0).getId());
    }
}
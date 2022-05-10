package sju.capstone.docswant.domain.member.service.patient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import sju.capstone.docswant.common.factory.DtoFactory;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.domain.member.model.dto.PatientDto;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.member.repository.doctor.DoctorRepository;
import sju.capstone.docswant.domain.member.repository.patient.PatientRepository;

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
        assertThat(result.getUsername()).isEqualTo(requestDto.getCode());
    }

}
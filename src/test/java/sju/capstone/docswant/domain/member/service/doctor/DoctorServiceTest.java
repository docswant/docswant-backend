package sju.capstone.docswant.domain.member.service.doctor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import sju.capstone.docswant.common.factory.DtoFactory;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.domain.member.model.dto.DoctorDto;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.member.repository.doctor.DoctorCodeRepository;
import sju.capstone.docswant.domain.member.repository.doctor.DoctorRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private DoctorCodeRepository doctorCodeRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @Test
    void 의사_코드_검증_테스트() {
        //given
        String code = "DOCTOR001";
        given(doctorCodeRepository.existsByCode(any(String.class))).willReturn(true);

        //when
        boolean isValid = doctorService.isValidCode(code);

        //then
        assertThat(isValid).isTrue();
    }

    @Test
    void 의사_회원가입_테스트() {
        //given
        DoctorDto.Request requestDto = DtoFactory.getDoctorRequestDto();

        //when
        DoctorDto.Response result = doctorService.register(requestDto);

        //then
        assertThat(result.getCode()).isEqualTo(requestDto.getCode());
        assertThat(result.getName()).isEqualTo(requestDto.getName());
        assertThat(result.getMajor()).isEqualTo(requestDto.getMajor());
    }

    @Test
    void 의사_정보수정_테스트() {
        //given
        Doctor doctor = EntityFactory.getDoctorEntity();
        DoctorDto.Request requestDto = DtoFactory.getDoctorUpdateRequestDto();
        given(doctorRepository.findByCode(any(String.class))).willReturn(Optional.of(doctor));

        //when
        DoctorDto.Response result = doctorService.update(doctor.getCode(), requestDto);

        //then
        assertThat(result.getUsername()).isEqualTo(requestDto.getUsername());
    }
}
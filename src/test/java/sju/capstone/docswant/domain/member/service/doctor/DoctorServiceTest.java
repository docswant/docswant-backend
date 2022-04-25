package sju.capstone.docswant.domain.member.service.doctor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import sju.capstone.docswant.domain.member.model.dto.DoctorDto;
import sju.capstone.docswant.domain.member.model.entity.doctor.Doctor;
import sju.capstone.docswant.domain.member.model.mapper.DoctorMapper;
import sju.capstone.docswant.domain.member.repository.doctor.DoctorRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    private DoctorDto.Request requestDto;

    @BeforeEach
    void setUp() {
        requestDto = DoctorDto.Request.builder().code("DOCTOR001").username("username").password("password").name("zooneon").major("orthopedics").build();
    }

    @Test
    void 의사_회원가입_테스트() {
        //given
        String password = "password";
        DoctorDto.Response expectedDto = DoctorDto.Response.builder().code("DOCTOR001").name("zooneon").major("orthopedics").build();

        //when
        DoctorDto.Response result = doctorService.register(requestDto);

        //then
        assertThat(result.getCode()).isEqualTo(expectedDto.getCode());
        assertThat(result.getName()).isEqualTo(expectedDto.getName());
        assertThat(result.getMajor()).isEqualTo(expectedDto.getMajor());
    }
}
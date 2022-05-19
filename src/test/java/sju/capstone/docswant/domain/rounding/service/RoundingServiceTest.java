package sju.capstone.docswant.domain.rounding.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sju.capstone.docswant.common.factory.DtoFactory;
import sju.capstone.docswant.common.factory.EntityFactory;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.member.repository.doctor.DoctorRepository;
import sju.capstone.docswant.domain.member.repository.patient.PatientRepository;
import sju.capstone.docswant.domain.rounding.model.dto.RoundingDto;
import sju.capstone.docswant.domain.rounding.model.entity.Rounding;
import sju.capstone.docswant.domain.rounding.model.entity.RoundingStatus;
import sju.capstone.docswant.domain.rounding.repository.RoundingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RoundingServiceTest {

    @Mock
    private RoundingRepository roundingRepository;
    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private RoundingServiceImpl roundingService;

    @Test
    void 회진_생성_테스트() {
        //given
        String code = "code";
        RoundingDto.Request requestDto = DtoFactory.getRoundingRequestDto();
        Patient patient = EntityFactory.getPatientEntity();
        given(doctorRepository.findByCode(any(String.class))).willReturn(Optional.of(EntityFactory.getDoctorEntity()));
        given(patientRepository.findByCode(any(String.class))).willReturn(Optional.of(patient));

        //when
        RoundingDto.Response responseDto = roundingService.create(code, requestDto);

        //then
        assertThat(responseDto.getRoundingDate()).isEqualTo(requestDto.getRoundingDate());
        assertThat(responseDto.getRoundingTime()).isEqualTo(requestDto.getRoundingTime());
        assertThat(responseDto.getPatientName()).isEqualTo(patient.getName());
        assertThat(responseDto.getRoundingStatus()).isEqualTo(RoundingStatus.TODO);
    }

    @Test
    void 회진_수정_테스트() {
        //given
        Long id = 1L;
        RoundingDto.UpdateRequest requestDto = DtoFactory.getRoundingUpdateRequestDto();
        given(roundingRepository.findById(any(Long.class))).willReturn(Optional.of(EntityFactory.getRoundingEntity()));

        //when
        RoundingDto.Response responseDto = roundingService.update(id, requestDto);

        //then
        assertThat(responseDto.getRoundingDate()).isEqualTo(requestDto.getRoundingDate());
        assertThat(responseDto.getRoundingTime()).isEqualTo(requestDto.getRoundingTime());
    }

    @Test
    void 회진_상태_변경_테스트() {
        //given
        Long id = 1L;
        Rounding rounding = EntityFactory.getRoundingEntity();
        RoundingStatus prevStatus = rounding.getRoundingStatus();
        given(roundingRepository.findById(any(Long.class))).willReturn(Optional.of(rounding));

        //when
        RoundingDto.Response responseDto = roundingService.changeStatus(id);

        //then
        assertThat(responseDto.getRoundingStatus()).isNotEqualTo(prevStatus);
        assertThat(responseDto.getRoundingStatus()).isEqualTo(RoundingStatus.DONE);
    }

    @Test
    void 회진_조회_테스트() {
        //given
        Long id = 1L;
        Rounding rounding = EntityFactory.getRoundingEntity();
        given(roundingRepository.findById(any(Long.class))).willReturn(Optional.of(rounding));

        //when
        RoundingDto.Response responseDto = roundingService.find(id);

        //then
        assertThat(responseDto.getRoundingDate()).isEqualTo(rounding.getRoundingSchedule().getRoundingDate());
        assertThat(responseDto.getPatientName()).isEqualTo(rounding.getPatient().getName());
        assertThat(responseDto.getRoundingStatus()).isEqualTo(rounding.getRoundingStatus());
    }

    @Test
    void 날짜별_회진_조회_테스트() {
        //given
        String code = "code";
        LocalDate roundingDate = LocalDate.of(2022, 5, 17);
        List<Rounding> roundings = EntityFactory.getRoundingEntities();
        given(roundingRepository.findAllByDoctorCodeAndRoundingDateOrderByRoundingTimeAsc(any(String.class), any(LocalDate.class))).willReturn(roundings);

        //when
        List<RoundingDto.ListResponse> listResponseDto = roundingService.findAllByDate(code, roundingDate);

        //then
        assertThat(listResponseDto.get(0).getHospitalRoom()).isEqualTo(roundings.get(0).getPatient().getHospitalRoom());
        assertThat(listResponseDto.get(0).getRoundings().get(0).getPatientName()).isEqualTo(roundings.get(0).getPatient().getName());
        assertThat(listResponseDto.get(0).getRoundings().get(0).getRoundingDate()).isEqualTo(roundings.get(0).getRoundingSchedule().getRoundingDate());
    }
}
package sju.capstone.docswant.core.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import sju.capstone.docswant.domain.member.model.entity.doctor.DoctorCode;
import sju.capstone.docswant.domain.member.repository.doctor.DoctorCodeRepository;

/**
 * 요구사항에서 의사 코드가 이미 저장되어 있다고 가정하기 때문에 Runner 클래스 활용<br/>
 * 추후에 불필요하면 삭제
 * 
 * @author zooneon
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class DataInitializeRunner implements ApplicationRunner {

    private final DoctorCodeRepository doctorCodeRepository;
    private static final String ONE = "DOCTOR001";
    private static final String TWO = "DOCTOR002";
    private static final String THREE = "DOCTOR003";
    private static final String FOUR = "DOCTOR004";
    private static final String FIVE = "DOCTOR005";


    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("start to save doctor codes");

        DoctorCode codeOne = DoctorCode.builder().code(ONE).build();
        DoctorCode codeTwo = DoctorCode.builder().code(TWO).build();
        DoctorCode codeThree = DoctorCode.builder().code(THREE).build();
        DoctorCode codeFour = DoctorCode.builder().code(FOUR).build();
        DoctorCode codeFive = DoctorCode.builder().code(FIVE).build();

        doctorCodeRepository.save(codeOne);
        doctorCodeRepository.save(codeTwo);
        doctorCodeRepository.save(codeThree);
        doctorCodeRepository.save(codeFour);
        doctorCodeRepository.save(codeFive);

        log.info("doctor codes saved successfully");
    }
}

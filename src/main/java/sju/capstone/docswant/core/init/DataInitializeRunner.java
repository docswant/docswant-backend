package sju.capstone.docswant.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import sju.capstone.docswant.domain.member.entity.Member;
import sju.capstone.docswant.domain.member.entity.doctor.Doctor;
import sju.capstone.docswant.domain.member.repository.MemberRepository;
import sju.capstone.docswant.domain.member.repository.doctor.DoctorRepository;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataInitializeRunner implements ApplicationRunner {

    private final MemberRepository memberRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Doctor doctor = Doctor.builder().code("doctor1").username("doctor").password("1234").name("kim").major("none").build();
        Member save = memberRepository.save(doctor);
        Doctor doctor1 = doctorRepository.findById(save.getCode()).orElseThrow();
        log.info("doctor.memberType={}", doctor1.getMemberType());

    }
}

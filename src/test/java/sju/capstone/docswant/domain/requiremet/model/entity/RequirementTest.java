package sju.capstone.docswant.domain.requiremet.model.entity;

import org.junit.jupiter.api.Test;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;
import sju.capstone.docswant.domain.requirement.model.entity.Requirement;
import sju.capstone.docswant.domain.requirement.model.entity.RequirementStatus;

import static org.assertj.core.api.Assertions.assertThat;

class RequirementTest {
    @Test
    void 환자_문의사항_추가_테스트() {
        //given
        Requirement requirement = Requirement.builder().content("문의사항입니다.").build();
        Patient patient = Patient.builder().code("PATIENT001").build();

        //when
        requirement.setPatient(patient);
        patient.addRequirement(requirement);

        //then
        assertThat(patient.getRequirements().get(0)).isEqualTo(requirement);
        assertThat(requirement.getStatus()).isEqualTo(RequirementStatus.UNREAD);
    }

    @Test
    void 문의사항_내용수정_테스트(){
        //given
        Requirement requirement = Requirement.builder().content("문의사항입니다.").build();
        String updateContent = "문의사항입니다.123123";

        //when
        requirement.updateContent(requirement,updateContent);

        //then
        assertThat(requirement.getContent()).isEqualTo(updateContent);
        assertThat(requirement.getStatus()).isEqualTo(RequirementStatus.UNREAD);
    }

    @Test
    void 문의사항_상태수정_테스트(){
        //given
        Requirement requirement = Requirement.builder().content("문의사항입니다.").build();

        //when
        requirement.changeStatusToRead();

        //then
        assertThat(requirement.getStatus()).isEqualTo(RequirementStatus.READ);
    }
}

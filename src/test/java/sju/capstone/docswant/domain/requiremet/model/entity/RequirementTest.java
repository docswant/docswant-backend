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
        Requirement requirement = Requirement.builder().title("requirement title").content("requirement content").build();
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
        Requirement requirement = Requirement.builder().title("requirement title").content("requirement content").build();
        String updateContent = "update content";
        String updateTitle = "update title";

        //when
        requirement.updateContent(requirement, updateTitle, updateContent);

        //then
        assertThat(requirement.getContent()).isEqualTo(updateContent);
        assertThat(requirement.getTitle()).isEqualTo(updateTitle);
        assertThat(requirement.getStatus()).isEqualTo(RequirementStatus.UNREAD);
    }

    @Test
    void 문의사항_상태수정_테스트(){
        //given
        Requirement requirement = Requirement.builder().title("requirement title").content("requirement content").build();

        //when
        requirement.changeStatusToRead();

        //then
        assertThat(requirement.getStatus()).isEqualTo(RequirementStatus.READ);
    }
}

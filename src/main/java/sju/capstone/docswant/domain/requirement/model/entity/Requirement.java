package sju.capstone.docswant.domain.requirement.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sju.capstone.docswant.common.entity.BaseTimeEntity;
import sju.capstone.docswant.domain.member.model.entity.patient.Patient;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("Requirement")
@PrimaryKeyJoinColumn(name = "requirement_id")
@Table(name = "requirement")
@Entity
public class Requirement extends BaseTimeEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "requirement_id")
        private Long id;

        @Column(name = "requirement_title", nullable = false, length = 50)
        private String title;

        @Column(name = "requirement_content", nullable = false, length = 500)
        private String content;

        @Enumerated(EnumType.STRING)
        @Column(name = "requirement_status", nullable = false)
        private RequirementStatus status;


        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "patient_code")
        private Patient patient;

        @Builder
        public Requirement(Long id, String title, String content, RequirementStatus status){
            this.id = id;
            this.title = title;
            this.content = content;
            this.status = RequirementStatus.UNREAD;
        }

        public void setPatient(Patient patient){
            this.patient = patient;
        }

        public void updateContent(Requirement requirement, String updateTitle, String updateContent){
            requirement.content=updateContent;
            requirement.title=updateTitle;
            requirement.status=RequirementStatus.UNREAD;
        }
        public void changeStatusToRead(){
            this.status=RequirementStatus.READ;
        }
}

package sju.capstone.docswant.domain.member.entity.doctor;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sju.capstone.docswant.domain.member.entity.Member;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("MEMBER_DOCTOR")
@PrimaryKeyJoinColumn(name = "doctor_code")
@Table(name = "doctor")
@Entity
public class Doctor extends Member {

    @Column(name = "doctor_name", nullable = false, length = 20)
    private String name;

    @Column(name = "doctor_major", nullable = false, length = 50)
    private String major;

    @Builder
    public Doctor(String code, String username, String password, String name, String major) {
        super(code, username, password);
        this.name = name;
        this.major = major;
    }
}

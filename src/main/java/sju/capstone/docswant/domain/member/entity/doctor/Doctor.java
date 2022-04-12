package sju.capstone.docswant.domain.member.entity.doctor;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sju.capstone.docswant.domain.member.entity.Member;
import sju.capstone.docswant.domain.member.entity.Role;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("doctor")
@PrimaryKeyJoinColumn(name = "doctor_code")
@Table(name = "doctor")
@Entity
public class Doctor extends Member {

    @Column(name = "doctor_name", nullable = false, length = 20)
    private String name;

    @Column(name = "doctor_major", nullable = false, length = 50)
    private String major;

    @Builder
    public Doctor(String code, String username, String password, Role role, String name, String major) {
        super(code, username, password, role);
        this.name = name;
        this.major = major;
    }
}

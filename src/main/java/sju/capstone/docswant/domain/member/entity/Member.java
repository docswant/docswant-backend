package sju.capstone.docswant.domain.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sju.capstone.docswant.common.entity.BaseTimeEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "member_type")
@Table(name = "member")
@Entity
public abstract class Member extends BaseTimeEntity {

    @Id
    @Column(name = "member_code", updatable = false, length = 30)
    private String code;

    @Column(name = "member_username", nullable = false, unique = true, length = 30)
    private String username;

    @Column(name = "member_password", nullable = false, length = 50)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "member_role", nullable = false, length = 15)
    private Role role;

    public Member(String code, String username, String password, Role role) {
        this.code = code;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}

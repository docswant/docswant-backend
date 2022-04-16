package sju.capstone.docswant.domain.member.model.entity;

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

    @Column(name = "member_type", updatable = false, insertable = false, length = 30)
    private String memberType;

    @Column(name = "member_refresh_token")
    private String refreshToken;

    public Member(String code, String username, String password) {
        this.code = code;
        this.username = username;
        this.password = password;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}

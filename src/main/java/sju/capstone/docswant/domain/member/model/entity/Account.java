package sju.capstone.docswant.domain.member.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import sju.capstone.docswant.common.entity.BaseTimeEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "account_type")
@Table(name = "account")
@Entity
public abstract class Account extends BaseTimeEntity implements Persistable<String> {

    @Id
    @Column(name = "account_code", updatable = false, length = 30)
    private String code;

    @Column(name = "account_username", nullable = false, unique = true, length = 30)
    private String username;

    @Column(name = "account_password", nullable = false, length = 50)
    private String password;

    @Column(name = "account_type", updatable = false, insertable = false, length = 30)
    private String accountType;

    @Column(name = "account_refresh_token")
    private String refreshToken;

    public Account(String code, String username, String password) {
        this.code = code;
        this.username = username;
        this.password = password;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String getId() {
        return code;
    }

    @Override
    public boolean isNew() {
        return this.getCreatedAt() == null;
    }

}

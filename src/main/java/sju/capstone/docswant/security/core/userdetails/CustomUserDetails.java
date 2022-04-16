package sju.capstone.docswant.security.core.userdetails;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import sju.capstone.docswant.domain.member.model.entity.Account;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {

    private final Account account;

    public CustomUserDetails(Account account, Collection<? extends GrantedAuthority> authorities) {
        super(account.getUsername(), account.getPassword(), authorities);
        this.account = account;
    }
    
}

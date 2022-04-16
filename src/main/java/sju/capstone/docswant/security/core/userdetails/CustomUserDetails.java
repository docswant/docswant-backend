package sju.capstone.docswant.security.core.userdetails;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import sju.capstone.docswant.domain.member.model.entity.Member;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {

    private final Member member;

    public CustomUserDetails(Member member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getUsername(), member.getPassword(), authorities);
        this.member = member;
    }
    
}

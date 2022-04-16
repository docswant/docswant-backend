package sju.capstone.docswant.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sju.capstone.docswant.domain.member.model.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

    Member findByUsername(String username);

}

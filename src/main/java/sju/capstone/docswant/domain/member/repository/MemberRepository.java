package sju.capstone.docswant.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sju.capstone.docswant.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

}

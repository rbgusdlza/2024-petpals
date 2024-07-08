package rbgusdlza.petpals.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import rbgusdlza.petpals.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}

package rbgusdlza.petpals.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rbgusdlza.petpals.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}

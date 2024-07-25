package rbgusdlza.petpals.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByLoginId(String loginId);

    List<Member> findByNickname(String nickname);

    List<Member> findByEmail(String email);

    Optional<Member> findByLoginIdAndEncryptedPassword(String loginId, String encryptedPassword);
}

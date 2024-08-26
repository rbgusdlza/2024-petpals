package rbgusdlza.petpals.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findAllByLoginId(String loginId);

    List<Member> findAllByNickname(String nickname);

    List<Member> findAllByEmail(String email);

    Optional<Member> findByLoginIdAndEncryptedPassword(String loginId, String encryptedPassword);

    long countByLoginIdOrNicknameOrEmail(String loginId, String nickname, String email);
}

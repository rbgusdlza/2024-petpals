package rbgusdlza.petpals.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByLoginId(String loginId);

    Member findByNickname(String nickname);

    Member findByEmail(String email);

    Member findByLoginIdAndEncryptedPassword(String loginId, String encryptedPassword);
}

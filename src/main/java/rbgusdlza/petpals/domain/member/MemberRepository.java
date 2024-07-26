package rbgusdlza.petpals.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByLoginId(String loginId);

    List<Member> findByNickname(String nickname);

    List<Member> findByEmail(String email);

    Optional<Member> findByLoginIdAndEncryptedPassword(String loginId, String encryptedPassword);

    @Query("SELECT COUNT(m) FROM Member m WHERE m.loginId = :loginId OR m.nickname = :nickname OR m.email = :email")
    long countByLoginIdOrNicknameOrEmail(@Param("loginId") String loginId, @Param("nickname") String nickname, @Param("email") String email);
}

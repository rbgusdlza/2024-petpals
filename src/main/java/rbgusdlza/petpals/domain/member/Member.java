package rbgusdlza.petpals.domain.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import rbgusdlza.petpals.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("entity_status = 'ACTIVE'")
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String loginId;
    private String nickname;
    private String encryptedPassword;
    private String email;

    @Builder
    private Member(String loginId, String nickname, String encryptedPassword, String email) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.encryptedPassword = encryptedPassword;
        this.email = email;
    }

    public static Member of(String loginId, String nickname, String encryptedPassword, String email) {
        return Member.builder()
                .loginId(loginId)
                .nickname(nickname)
                .encryptedPassword(encryptedPassword)
                .email(email)
                .build();
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}

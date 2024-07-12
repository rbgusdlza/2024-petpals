package rbgusdlza.petpals.domain.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rbgusdlza.petpals.domain.BaseEntity;
import rbgusdlza.petpals.domain.error.PetPalsException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

    public static final int MINIMUM_LOGIN_ID_LENGTH = 8;
    public static final int MAXIMUM_LOGIN_ID_LENGTH = 15;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String loginId;
    private String nickname;
    private String password;
    private String email;

    public static Member of(String loginId, String nickname, String password, String email) {
        Member member = Member.builder()
                .loginId(loginId)
                .nickname(nickname)
                .password(password)
                .email(email)
                .build();
        member.checkIfMemberIsValid();
        return member;
    }

    private void checkIfMemberIsValid() {
        checkIfLoginIdIsValid();
//        checkIfNicknameIsValid(nickname);
//        checkIfPasswordIsValid(password);
//        checkIfEmailIsValid(email);
    }

    private void checkIfLoginIdIsValid() {
        if (isInvalidLoginIdLength(loginId)) {
            throw new PetPalsException("아이디는 8자에서 15자 사이여야 합니다.");
        }

        if (isInvalidLoginIdFormat(loginId)) {
            throw new PetPalsException("아이디는 영어 대문자, 소문자, 숫자로만 구성되어야 합니다.");
        }
    }

    private boolean isInvalidLoginIdFormat(String loginId) {
        return !loginId.matches("[a-zA-Z0-9]+");
    }

    private boolean isInvalidLoginIdLength(String loginId) {
        return loginId.length() < MINIMUM_LOGIN_ID_LENGTH || loginId.length() > MAXIMUM_LOGIN_ID_LENGTH;
    }

    @Builder
    private Member(String loginId, String nickname, String password, String email) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
    }
}

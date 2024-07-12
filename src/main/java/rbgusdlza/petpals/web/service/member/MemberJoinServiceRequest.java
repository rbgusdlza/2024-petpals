package rbgusdlza.petpals.web.service.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rbgusdlza.petpals.domain.error.PetPalsException;
import rbgusdlza.petpals.domain.member.Member;

@Getter
@NoArgsConstructor
public class MemberJoinServiceRequest {

    public static final int MINIMUM_LOGIN_ID_LENGTH = 8;
    public static final int MAXIMUM_LOGIN_ID_LENGTH = 15;

    private String loginId;
    private String nickname;
    private String password;
    private String email;

    public Member toEntity() {
        checkIfMemberInputIsValid();
        return Member.builder()
                .loginId(loginId)
                .nickname(nickname)
                .password(password)
                .email(email)
                .build();
    }

    private void checkIfMemberInputIsValid() {
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
    private MemberJoinServiceRequest(String loginId, String nickname, String password, String email) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
    }
}

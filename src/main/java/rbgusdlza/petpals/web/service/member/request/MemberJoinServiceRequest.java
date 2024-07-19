package rbgusdlza.petpals.web.service.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rbgusdlza.petpals.domain.member.Member;
import rbgusdlza.petpals.global.util.PasswordEncryptor;

@Getter
@NoArgsConstructor
public class MemberJoinServiceRequest {

    private String loginId;
    private String nickname;
    private String password;
    private String email;

    @Builder
    private MemberJoinServiceRequest(String loginId, String nickname, String password, String email) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
    }

    public Member toEntity() {
        String encryptedPassword = PasswordEncryptor.encryptPasswordFrom(password);
        return Member.builder()
                .loginId(loginId)
                .nickname(nickname)
                .encryptedPassword(encryptedPassword)
                .email(email)
                .build();
    }
}

package rbgusdlza.petpals.web.service.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rbgusdlza.petpals.domain.member.Member;

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
        return Member.builder()
                .loginId(loginId)
                .nickname(nickname)
                .password(password)
                .email(email)
                .build();
    }
}

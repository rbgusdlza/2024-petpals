package rbgusdlza.petpals.web.controller.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rbgusdlza.petpals.web.service.member.request.MemberJoinServiceRequest;

@Getter @Setter
@NoArgsConstructor
public class MemberJoinRequest {

    private String loginId;
    private String nickname;
    private String password;
    private String email;
    private boolean agreement;

    public MemberJoinServiceRequest toServiceRequest() {
        return MemberJoinServiceRequest.builder()
                .loginId(loginId)
                .nickname(nickname)
                .password(password)
                .email(email)
                .build();
    }

    @Builder
    private MemberJoinRequest(String loginId, String nickname, String password, String email, boolean agreement) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.agreement = agreement;
    }
}

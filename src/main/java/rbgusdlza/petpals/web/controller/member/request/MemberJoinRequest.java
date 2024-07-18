package rbgusdlza.petpals.web.controller.member.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rbgusdlza.petpals.web.service.member.request.MemberJoinServiceRequest;

@Getter
@Setter
@NoArgsConstructor
public class MemberJoinRequest {

    @NotBlank
    private String loginId;

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

    @AssertTrue
    private boolean agreement;

    @Builder
    private MemberJoinRequest(String loginId, String nickname, String password, String email, boolean agreement) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.agreement = agreement;
    }

    public MemberJoinServiceRequest toServiceRequest() {
        return MemberJoinServiceRequest.builder()
                .loginId(loginId)
                .nickname(nickname)
                .password(password)
                .email(email)
                .build();
    }
}

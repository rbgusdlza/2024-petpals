package rbgusdlza.petpals.web.controller.member.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rbgusdlza.petpals.web.service.member.MemberJoinServiceRequest;

@Getter @Setter
@NoArgsConstructor
public class MemberJoinRequest {

    @NotBlank(message = "아이디는 필수입니다.")
    private String joinId;

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @AssertTrue(message = "가입을 원하시면 약관에 동의해주세요.")
    private boolean agreement;

    public MemberJoinServiceRequest toServiceRequest() {
        return MemberJoinServiceRequest.builder()
                .joinId(joinId)
                .nickname(nickname)
                .password(password)
                .email(email)
                .build();
    }

    @Builder
    private MemberJoinRequest(String joinId, String nickname, String password, String email, boolean agreement) {
        this.joinId = joinId;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.agreement = agreement;
    }
}

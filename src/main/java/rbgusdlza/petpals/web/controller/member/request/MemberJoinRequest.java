package rbgusdlza.petpals.web.controller.member.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberJoinRequest {

    @NotEmpty(message = "아이디를 입력하세요.")
    private String joinId;

    @NotEmpty(message = "닉네임을 입력하세요.")
    private String nickname;

    @NotEmpty(message = "비밀번호를 입력하세요.")
    private String password;

    @NotEmpty(message = "이메일을 입력하세요.")
    private String email;
}

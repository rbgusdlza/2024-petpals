package rbgusdlza.petpals.web.controller.member.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginRequest {

    @NotEmpty(message = "아이디를 입력하세요.")
    private String loginId;

    @NotEmpty(message = "비밀번호를 입력하세요.")
    private String password;

}

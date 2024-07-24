package rbgusdlza.petpals.web.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rbgusdlza.petpals.global.util.PasswordEncryptor;
import rbgusdlza.petpals.web.service.member.request.MemberLoginServiceRequest;

@Getter
@Setter
@NoArgsConstructor
public class MemberLoginRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Builder
    private MemberLoginRequest(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public MemberLoginServiceRequest toServiceRequest() {
        String encryptedPassword = PasswordEncryptor.encryptPasswordFrom(password);
        return MemberLoginServiceRequest.builder()
                .loginId(loginId)
                .encryptedPassword(encryptedPassword)
                .build();
    }
}

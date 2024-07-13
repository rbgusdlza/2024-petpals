package rbgusdlza.petpals.web.controller.member.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rbgusdlza.petpals.web.service.member.request.MemberJoinServiceRequest;

@Getter
@Setter
@NoArgsConstructor
public class MemberJoinRequest {

    @NotBlank(message = "아이디는 필수입니다.")
    @Pattern(
            regexp = "^[a-zA-Z0-9]{5,20}$",
            message = "아이디는 5~20자의 영문 대/소문자, 숫자만 사용 가능합니다."
    )
    private String loginId;

    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(
            min = 2, max = 10,
            message ="닉네임은 2자에서 10자 사이여야 합니다."
    )
    private String nickname;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,16}$",
            message = "비밀번호는 8~16자의 영문 대/소문자, 숫자, 특수문자가 각각 1개 이상 포함되어야 합니다."
    )
    private String password;

    @NotBlank(message = "이메일은 필수입니다.")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "올바르지 않은 형식의 이메일입니다."
    )
    private String email;

    @AssertTrue(message = "가입을 원하시면 약관에 동의해주세요.")
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

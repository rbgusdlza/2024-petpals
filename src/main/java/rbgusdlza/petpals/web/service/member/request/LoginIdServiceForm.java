package rbgusdlza.petpals.web.service.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginIdServiceForm {

    private String loginId;

    @Builder
    private LoginIdServiceForm(String loginId) {
        this.loginId = loginId;
    }

    public static LoginIdServiceForm of(String loginId) {
        return LoginIdServiceForm.builder()
                .loginId(loginId)
                .build();
    }
}

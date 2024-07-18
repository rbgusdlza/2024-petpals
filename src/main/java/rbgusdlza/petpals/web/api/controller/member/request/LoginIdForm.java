package rbgusdlza.petpals.web.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rbgusdlza.petpals.web.service.member.request.LoginIdServiceForm;

@Getter
@Setter
@NoArgsConstructor
public class LoginIdForm {

    @NotBlank
    private String loginId;

    @Builder
    private LoginIdForm(String loginId) {
        this.loginId = loginId;
    }

    public LoginIdServiceForm toServiceForm() {
        return LoginIdServiceForm.builder()
                .loginId(loginId)
                .build();
    }
}

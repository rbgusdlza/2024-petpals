package rbgusdlza.petpals.web.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailAuthCodeForm {

    @NotBlank
    private String email;
    @NotBlank
    private String authCode;

    @Builder
    private EmailAuthCodeForm(String email, String authCode) {
        this.email = email;
        this.authCode = authCode;
    }

    public static EmailAuthCodeForm of(String email, String authCode) {
        return EmailAuthCodeForm.builder()
                .email(email)
                .authCode(authCode)
                .build();
    }
}

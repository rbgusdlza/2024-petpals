package rbgusdlza.petpals.web.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rbgusdlza.petpals.web.service.member.request.EmailServiceForm;

@Getter
@Setter
@NoArgsConstructor
public class EmailForm {

    @NotBlank
    private String email;

    @Builder
    private EmailForm(String email) {
        this.email = email;
    }

    public EmailServiceForm toServiceForm() {
        return EmailServiceForm.builder()
                .email(email)
                .build();
    }
}

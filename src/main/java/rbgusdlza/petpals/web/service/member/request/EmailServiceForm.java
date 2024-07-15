package rbgusdlza.petpals.web.service.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailServiceForm {

    private String email;

    @Builder
    private EmailServiceForm(String email) {
        this.email = email;
    }
}

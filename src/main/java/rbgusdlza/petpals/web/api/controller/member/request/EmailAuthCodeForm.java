package rbgusdlza.petpals.global.event.email.request;

import jakarta.validation.constraints.NotBlank;
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
}

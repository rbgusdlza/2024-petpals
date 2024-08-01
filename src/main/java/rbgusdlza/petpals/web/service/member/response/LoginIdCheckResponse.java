package rbgusdlza.petpals.web.service.member.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginIdCheckResponse {

    private Boolean isDuplicated;

    @Builder
    private LoginIdCheckResponse(boolean isDuplicated) {
        this.isDuplicated = isDuplicated;
    }

    public static LoginIdCheckResponse of(boolean isDuplicated) {
        return LoginIdCheckResponse.builder()
                .isDuplicated(isDuplicated)
                .build();
    }
}

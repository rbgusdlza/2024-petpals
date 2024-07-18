package rbgusdlza.petpals.web.service.member.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginIdCheckResponse {

    private Boolean isDuplicated;

    @Builder
    private LoginIdCheckResponse(Boolean isDuplicated) {
        this.isDuplicated = isDuplicated;
    }

    public static LoginIdCheckResponse from(Boolean isDuplicated) {
        return LoginIdCheckResponse.builder()
                .isDuplicated(isDuplicated)
                .build();
    }
}

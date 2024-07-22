package rbgusdlza.petpals.web.service.member.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EmailCheckResponse {

    private Boolean isDuplicated;

    @Builder
    private EmailCheckResponse(boolean isDuplicated) {
        this.isDuplicated = isDuplicated;
    }

    public static EmailCheckResponse from(boolean isDuplicated) {
        return EmailCheckResponse.builder()
                .isDuplicated(isDuplicated)
                .build();
    }
}

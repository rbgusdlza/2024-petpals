package rbgusdlza.petpals.web.controller.post.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRegisterRequest {

    @NotNull
    private Long memberId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @Builder
    private PostRegisterRequest(Long memberId, String title, String content) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }

}

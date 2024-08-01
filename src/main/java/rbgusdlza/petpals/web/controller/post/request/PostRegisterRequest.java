package rbgusdlza.petpals.web.controller.post.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRegisterRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @Builder
    private PostRegisterRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

}

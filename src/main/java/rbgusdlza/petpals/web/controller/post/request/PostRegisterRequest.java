package rbgusdlza.petpals.web.controller.post.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rbgusdlza.petpals.web.service.post.request.PostRegisterServiceRequest;

@Getter
@Setter
@NoArgsConstructor
public class PostRegisterRequest {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @Builder
    private PostRegisterRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostRegisterServiceRequest toServiceRequest(Long memberId) {
        return PostRegisterServiceRequest.of(memberId, title, content);
    }
}

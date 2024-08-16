package rbgusdlza.petpals.web.controller.post.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import rbgusdlza.petpals.web.service.post.request.PostRegisterServiceRequest;

@Getter
@Setter
@NoArgsConstructor
public class PostRegisterRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private MultipartFile imageFile;

    @Builder
    private PostRegisterRequest(String title, String content, MultipartFile imageFile) {
        this.title = title;
        this.content = content;
        this.imageFile = imageFile;
    }

    public PostRegisterServiceRequest toServiceRequest(Long memberId) {
        return PostRegisterServiceRequest.of(memberId, title, content, imageFile);
    }
}

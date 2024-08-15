package rbgusdlza.petpals.web.service.post.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import rbgusdlza.petpals.domain.post.Post;
import rbgusdlza.petpals.web.service.photo.request.PhotoRegisterServiceRequest;

@Getter
@NoArgsConstructor
public class PostRegisterServiceRequest {

    private Long memberId;
    private String title;
    private String content;
    private MultipartFile imageFile;

    @Builder
    private PostRegisterServiceRequest(Long memberId, String title, String content, MultipartFile imageFile) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.imageFile = imageFile;
    }

    public static PostRegisterServiceRequest of(Long memberId, String title, String content, MultipartFile imageFile) {
        return PostRegisterServiceRequest.builder()
                .memberId(memberId)
                .title(title)
                .content(content)
                .imageFile(imageFile)
                .build();
    }

    public Post toPost() {
        return Post.of(memberId, title, content);
    }

    public PhotoRegisterServiceRequest toPhotoRegisterServiceRequest() {
        return PhotoRegisterServiceRequest.of(imageFile);
    }
}

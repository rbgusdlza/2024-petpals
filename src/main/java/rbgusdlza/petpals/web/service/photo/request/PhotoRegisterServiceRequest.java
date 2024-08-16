package rbgusdlza.petpals.web.service.photo.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class PhotoRegisterServiceRequest {

    private Long postId;
    private MultipartFile imageFile;

    @Builder
    private PhotoRegisterServiceRequest(Long postId, MultipartFile imageFile) {
        this.postId = postId;
        this.imageFile = imageFile;
    }

    public static PhotoRegisterServiceRequest of(Long postId, MultipartFile imageFile) {
        return PhotoRegisterServiceRequest.builder()
                .postId(postId)
                .imageFile(imageFile)
                .build();
    }
}

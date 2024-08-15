package rbgusdlza.petpals.web.service.photo.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class PhotoRegisterServiceRequest {

    private MultipartFile imageFile;

    @Builder
    private PhotoRegisterServiceRequest(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public static PhotoRegisterServiceRequest of(MultipartFile imageFile) {
        return PhotoRegisterServiceRequest.builder()
                .imageFile(imageFile)
                .build();
    }
}

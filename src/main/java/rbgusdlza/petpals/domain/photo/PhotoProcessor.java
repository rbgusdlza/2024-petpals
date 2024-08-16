package rbgusdlza.petpals.domain.photo;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PhotoProcessor {

    public Photo getPhoto(Long postId, MultipartFile imageFile) {
        String originalFileName = imageFile.getOriginalFilename();
        return Photo.of(postId, originalFileName);
    }

    public PhotoDetails getPhotoDetails(Long photoId, String uniqueFileName) {
        return PhotoDetails.of(photoId, uniqueFileName);
    }
}

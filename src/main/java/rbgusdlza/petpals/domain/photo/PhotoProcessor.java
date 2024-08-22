package rbgusdlza.petpals.domain.photo;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PhotoProcessor {

    public Photo createPhoto(Long postId, MultipartFile imageFile) {
        String originalFileName = imageFile.getOriginalFilename();
        return Photo.of(postId, originalFileName);
    }

    public PhotoDetails createPhotoDetails(Long photoId, String storeFileName) {
        return PhotoDetails.of(photoId, storeFileName);
    }
}

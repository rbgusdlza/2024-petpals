package rbgusdlza.petpals.domain.photo;

import org.springframework.stereotype.Component;

@Component
public class PhotoProcessor {

    public Photo createPhotoFrom(Long postId, String originalFileName) {
        return Photo.of(postId, originalFileName);
    }

    public PhotoDetails createPhotoDetails(Long photoId, String storeFileName) {
        return PhotoDetails.of(photoId, storeFileName);
    }
}

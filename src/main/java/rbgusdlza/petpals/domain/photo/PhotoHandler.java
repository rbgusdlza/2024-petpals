package rbgusdlza.petpals.domain.photo;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoHandler {
    void saveImageFile(MultipartFile imageFile, String storeFileName);
}

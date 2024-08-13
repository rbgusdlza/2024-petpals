package rbgusdlza.petpals.domain.photo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class PhotoHandler {

    @Value("${file.directory}")
    private String fileDirectory;

    public void storeImageFile(MultipartFile imageFile) throws IOException {
        String originalFilename = imageFile.getOriginalFilename();
        String storeFileName = generateStoreFileName(originalFilename);
        imageFile.transferTo(new File(getFullPath(storeFileName)));
    }

    private String getFullPath(String fileName) {
        return fileDirectory + fileName;
    }
}

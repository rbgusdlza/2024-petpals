package rbgusdlza.petpals.domain.photo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Component
public class PhotoHandler {

    @Value("${file.directory}")
    private String fileDirectory;

    public void saveImageFile(MultipartFile imageFile, String storeFileName) {
        try {
            String fullPath = getFullPathFrom(storeFileName);
            File storeImageFile = generateImageFileAt(fullPath);
            imageFile.transferTo(storeImageFile);
        } catch (IOException e) {
            log.error("파일 입출력 오류 발생");
        }
    }

    private String getFullPathFrom(String fileName) {
        return fileDirectory + fileName;
    }

    private File generateImageFileAt(String fullPath) {
        return new File(fullPath);
    }
}

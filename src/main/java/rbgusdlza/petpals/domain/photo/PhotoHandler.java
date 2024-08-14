package rbgusdlza.petpals.domain.photo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class PhotoHandler {

    private static final String FILE_EXTENSION_SEPARATOR = ".";

    private final PhotoRepository photoRepository;

    @Value("${file.directory}")
    private String fileDirectory;

    public void saveImageFile(MultipartFile imageFile) {
        try {
            String originalFileName = imageFile.getOriginalFilename();
            String storeFileName = generateStoreFileNameFrom(originalFileName);
            String fullPath = getFullPathFrom(storeFileName);
            File storeImageFile = generateImageFileAt(fullPath);
            imageFile.transferTo(storeImageFile);
        } catch (IOException e) {
            log.error("파일 입출력 오류 발생");
        }
    }

    private File generateImageFileAt(String fullPath) {
        return new File(fullPath);
    }

    private String generateStoreFileNameFrom(String originalFileName) {
        String fileExtension = extractFileExtension(originalFileName);
        String randomFileName = generateUniqueFileName();
        return randomFileName + FILE_EXTENSION_SEPARATOR + fileExtension;
    }

    private String extractFileExtension(String originalFileName) {
        int fileExtensionStartIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        return originalFileName.substring(fileExtensionStartIndex + 1);
    }

    private String generateUniqueFileName() {
        return UUID.randomUUID().toString();
    }

    private String getFullPathFrom(String fileName) {
        return fileDirectory + fileName;
    }
}

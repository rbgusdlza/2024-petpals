package rbgusdlza.petpals.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class FileNameGenerator {

    private static final String FILE_EXTENSION_SEPARATOR = ".";

    public static String generateStoreFileNameFrom(String originalFileName) {
        String fileExtension = extractFileExtension(originalFileName);
        String uniqueFileName = generateUniqueFileName();
        return uniqueFileName + FILE_EXTENSION_SEPARATOR + fileExtension;
    }

    private static String generateUniqueFileName() {
        return UUID.randomUUID().toString();
    }

    private static String extractFileExtension(String originalFileName) {
        int fileExtensionStartIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        return originalFileName.substring(fileExtensionStartIndex + 1);
    }
}

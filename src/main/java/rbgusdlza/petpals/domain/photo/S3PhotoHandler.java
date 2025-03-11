package rbgusdlza.petpals.domain.photo;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Operations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3PhotoHandler implements PhotoHandler {

    private final S3Operations s3Operations;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    @Override
    public void saveImageFile(MultipartFile imageFile, String storeFileName) {
        try {
            checkIfImageFileIsEmpty(imageFile);
            uploadFileTo(imageFile, storeFileName);
        } catch (IOException e) {
            log.error("파일 입출력 오류 발생");
        }
    }

    private void checkIfImageFileIsEmpty(MultipartFile imageFile) {
        if (imageFile.isEmpty()) {
            throw new IllegalArgumentException("이미지 파일 없음");
        }
    }

    private void uploadFileTo(MultipartFile imageFile, String storeFileName) throws IOException {
        try (InputStream inputStream = imageFile.getInputStream()) {
            s3Operations.upload(bucketName, storeFileName, inputStream,
                    ObjectMetadata.builder().contentType(imageFile.getContentType()).build());
        }
    }
}

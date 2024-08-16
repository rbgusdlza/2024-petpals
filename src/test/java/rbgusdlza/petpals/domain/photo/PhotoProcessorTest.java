package rbgusdlza.petpals.domain.photo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class PhotoProcessorTest {

    @Autowired
    private PhotoProcessor photoProcessor;

    @DisplayName("게시물 아이디와 이미지 파일로 사진을 생성한다.")
    @Test
    void createPhoto() {
        //given
        Long postId = 1L;
        MultipartFile file = getFile("image.png");

        //when
        Photo photo = photoProcessor.createPhoto(postId, file);

        //then
        assertThat(photo).isNotNull()
                .extracting("postId", "photoName")
                .containsExactly(postId, "image.png");
    }

    @DisplayName("사진 아이디와 고유 파일 이름으로 사진 세부내용을 생성한다.")
    @Test
    void createPhotoDetails() {
        //given
        Long photoId = 1L;
        String uniqueFileName = "hello.png";

        //when
        PhotoDetails photoDetails = photoProcessor.createPhotoDetails(photoId, uniqueFileName);

        //then
        assertThat(photoDetails).isNotNull()
                .extracting("photoId", "storeFileName")
                .containsExactly(photoId, "hello.png");
    }

    private MockMultipartFile getFile(String originalFileName) {
        return new MockMultipartFile("file", originalFileName, "image/jpeg", "Hello, World!".getBytes());
    }
}
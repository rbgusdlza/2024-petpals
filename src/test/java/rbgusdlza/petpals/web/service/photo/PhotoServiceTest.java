package rbgusdlza.petpals.web.service.photo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import rbgusdlza.petpals.domain.photo.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.doNothing;

@ActiveProfiles("test")
@SpringBootTest
class PhotoServiceTest {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private PhotoDetailsRepository photoDetailsRepository;

    @MockBean
    private PhotoHandler photoHandler;

    @AfterEach
    void tearDown() {
        photoRepository.deleteAllInBatch();
        photoDetailsRepository.deleteAllInBatch();
    }

    @DisplayName("사진을 등록한다.")
    @Test
    void register() {
        //given
        MockMultipartFile imageFile = getFile("image.png");
        doNothing().when(photoHandler).saveImageFile(imageFile, "hello.png");

        //when
        Long photoId = photoService.register(1L, imageFile);

        //then
        assertThat(photoId).isNotNull();
    }

    @DisplayName("게시물 아이디로 사진과 사진 정보를 조회한다.")
    @Test
    void findPhotoWithDetailsBy() {
        //given
        Long postId = 1L;
        Photo photo = Photo.of(postId, "photo");
        photoRepository.save(photo);
        Long photoId = photo.getId();

        PhotoDetails photoDetails = PhotoDetails.of(photoId, "photo.png");
        photoDetailsRepository.save(photoDetails);

        //when
        PhotoWithDetails photoWithDetails = photoService.findPhotoWithDetailsBy(postId);

        //then
        Photo findPhoto = photoWithDetails.getPhoto();
        assertThat(findPhoto).isNotNull()
                .extracting("postId", "photoName")
                .containsExactly(postId, "photo");

        PhotoDetails findPhotoDetails = photoWithDetails.getPhotoDetails();
        assertThat(findPhotoDetails).isNotNull()
                .extracting("photoId", "storeFileName")
                .containsExactly(photoId, "photo.png");
    }

    private MockMultipartFile getFile(String originalFileName) {
        return new MockMultipartFile("file", originalFileName, "image/jpeg", "Hello, World!".getBytes());
    }
}
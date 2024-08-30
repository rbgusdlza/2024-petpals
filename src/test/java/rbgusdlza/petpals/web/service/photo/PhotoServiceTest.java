package rbgusdlza.petpals.web.service.photo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import rbgusdlza.petpals.domain.photo.PhotoHandler;
import rbgusdlza.petpals.domain.photo.PhotoRepository;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.doNothing;

@ActiveProfiles("test")
@SpringBootTest
class PhotoServiceTest {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private PhotoRepository photoRepository;

    @MockBean
    private PhotoHandler photoHandler;

    @AfterEach
    void tearDown() {
        photoRepository.deleteAllInBatch();
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

    private MockMultipartFile getFile(String originalFileName) {
        return new MockMultipartFile("file", originalFileName, "image/jpeg", "Hello, World!".getBytes());
    }
}
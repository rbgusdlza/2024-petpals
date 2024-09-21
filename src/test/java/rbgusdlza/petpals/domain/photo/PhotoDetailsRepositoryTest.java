package rbgusdlza.petpals.domain.photo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class PhotoDetailsRepositoryTest {

    @Autowired
    private PhotoDetailsRepository photoDetailsRepository;

    @AfterEach
    void tearDown() {
        photoDetailsRepository.deleteAllInBatch();
    }

    @DisplayName("사진 아이디로 사진 정보를 조회한다.")
    @Test
    void findByPhotoId() {
        //given
        PhotoDetails photoDetails = PhotoDetails.of(1L, "photo.png");
        photoDetailsRepository.save(photoDetails);

        //when
        PhotoDetails findPhotoDetails = photoDetailsRepository.findByPhotoId(1L).get();

        //then
        assertThat(findPhotoDetails).isNotNull()
                .extracting("photoId", "storeFileName")
                .containsExactlyInAnyOrder(1L, "photo.png");
    }
}
package rbgusdlza.petpals.domain.photo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rbgusdlza.petpals.IntegrationTestSupport;

import static org.assertj.core.api.Assertions.assertThat;

class PhotoRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private PhotoRepository photoRepository;

    @AfterEach
    void tearDown() {
        photoRepository.deleteAllInBatch();
    }

    @DisplayName("게시물 아이디로 사진을 조회한다.")
    @Test
    void findByPostId() {
        //given
        Photo photo = Photo.of(1L, "photo");
        photoRepository.save(photo);

        //when
        Photo findPhoto = photoRepository.findByPostId(1L).get();

        //then
        assertThat(findPhoto).isNotNull()
                .extracting("postId", "photoName")
                .containsExactlyInAnyOrder(1L, "photo");
    }
}
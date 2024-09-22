package rbgusdlza.petpals.web.service.photo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import rbgusdlza.petpals.domain.photo.*;
import rbgusdlza.petpals.domain.post.Post;
import rbgusdlza.petpals.domain.post.PostRepository;
import rbgusdlza.petpals.IntegrationTestSupport;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.doNothing;

class PhotoServiceTest extends IntegrationTestSupport {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private PhotoDetailsRepository photoDetailsRepository;

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    void tearDown() {
        photoRepository.deleteAllInBatch();
        photoDetailsRepository.deleteAllInBatch();
        postRepository.deleteAllInBatch();
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

    @DisplayName("게시물 아이디로 사진을 삭제한다.")
    @Test
    void remove() {
        //given
        Post post = Post.of(1L, "title", "content");
        postRepository.save(post);
        Long postId = post.getId();

        Photo photo = Photo.of(postId, "photo");
        photoRepository.save(photo);
        Long photoId = photo.getId();

        PhotoDetails photoDetails = PhotoDetails.of(photoId, "photo.png");
        photoDetailsRepository.save(photoDetails);

        //when
        Long removedPhotoId = photoService.remove(postId);

        //then
        assertThat(photoRepository.findByPostId(removedPhotoId)).isEmpty();
    }

    private MockMultipartFile getFile(String originalFileName) {
        return new MockMultipartFile("file", originalFileName, "image/jpeg", "Hello, World!".getBytes());
    }
}
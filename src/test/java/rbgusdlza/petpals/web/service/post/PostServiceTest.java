package rbgusdlza.petpals.web.service.post;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import rbgusdlza.petpals.domain.member.MemberRepository;
import rbgusdlza.petpals.domain.photo.Photo;
import rbgusdlza.petpals.domain.photo.PhotoDetails;
import rbgusdlza.petpals.domain.photo.PhotoDetailsRepository;
import rbgusdlza.petpals.domain.photo.PhotoRepository;
import rbgusdlza.petpals.domain.post.Post;
import rbgusdlza.petpals.domain.post.PostRepository;
import rbgusdlza.petpals.domain.reaction.Reaction;
import rbgusdlza.petpals.domain.reaction.ReactionRepository;
import rbgusdlza.petpals.domain.reaction.ReactionType;
import rbgusdlza.petpals.web.service.post.request.PostRegisterServiceRequest;
import rbgusdlza.petpals.web.service.post.response.PostResponse;

import static org.assertj.core.api.Assertions.*;
import static rbgusdlza.petpals.domain.reaction.ReactionType.*;
import static rbgusdlza.petpals.domain.reaction.TargetType.POST;


@ActiveProfiles("test")
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private PhotoDetailsRepository photoDetailsRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    @AfterEach
    void tearDown() {
        photoDetailsRepository.deleteAllInBatch();
        photoRepository.deleteAllInBatch();
        postRepository.deleteAllInBatch();
    }

    @DisplayName("게시물을 등록한다.")
    @Test
    void register() {
        //given
        MockMultipartFile file = getFile("image.png");
        PostRegisterServiceRequest request = PostRegisterServiceRequest.of(1L, "title", "content", file);

        //when
        Long postId = postService.register(request);

        //then
        assertThat(postId).isNotNull();
    }

    @DisplayName("게시물 id로 게시물을 조회한다.")
    @Test
    void findBy() {
        //given
        Post post = Post.of(1L, "hello", "world");
        postRepository.save(post);
        Long postId = post.getId();

        Photo photo = Photo.of(postId, "photoName");
        photoRepository.save(photo);
        Long photoId = photo.getId();

        PhotoDetails photoDetails = PhotoDetails.of(photoId, "image.png");
        photoDetailsRepository.save(photoDetails);

        Reaction reaction = Reaction.of(2L, postId, POST, LIKE);
        reactionRepository.save(reaction);

        //when
        PostResponse response = postService.findBy(postId);

        //then
        assertThat(response).isNotNull()
                .extracting("id", "title", "content", "uploadFileName", "storeFileName", "likeCount")
                .containsExactly(postId, "hello", "world", "photoName", "image.png", 1L);
    }

    private MockMultipartFile getFile(String originalFileName) {
        return new MockMultipartFile("file", originalFileName, "image/jpeg", "Hello, World!".getBytes());
    }
}
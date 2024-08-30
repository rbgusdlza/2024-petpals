package rbgusdlza.petpals.web.service.post;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import rbgusdlza.petpals.domain.photo.Photo;
import rbgusdlza.petpals.domain.photo.PhotoDetails;
import rbgusdlza.petpals.domain.photo.PhotoDetailsRepository;
import rbgusdlza.petpals.domain.photo.PhotoRepository;
import rbgusdlza.petpals.domain.post.Post;
import rbgusdlza.petpals.domain.post.PostRepository;
import rbgusdlza.petpals.domain.reaction.Reaction;
import rbgusdlza.petpals.domain.reaction.ReactionRepository;
import rbgusdlza.petpals.web.service.post.request.PostCursorServiceRequest;
import rbgusdlza.petpals.web.service.post.request.PostRegisterServiceRequest;
import rbgusdlza.petpals.web.service.post.response.PostResponse;

import java.util.List;

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

    @DisplayName("최근 등록된 게시물을 limit만큼 조회한다.")
    @Test
    void findRecentPosts() {
        //given
        Post post1 = Post.of(1L, "hello1", "world1");
        Post post2 = Post.of(1L, "hello2", "world2");
        Post post3 = Post.of(2L, "hello3", "world3");
        postRepository.saveAll(List.of(post1, post2, post3));
        Long postId1 = post1.getId();
        Long postId2 = post2.getId();
        Long postId3 = post3.getId();

        Photo photo1 = Photo.of(postId1, "photoName1");
        Photo photo2 = Photo.of(postId2, "photoName2");
        Photo photo3 = Photo.of(postId3, "photoName3");
        photoRepository.saveAll(List.of(photo1, photo2, photo3));
        Long photoId1 = photo1.getId();
        Long photoId2 = photo2.getId();
        Long photoId3 = photo3.getId();

        PhotoDetails photoDetails1 = PhotoDetails.of(photoId1, "image1.png");
        PhotoDetails photoDetails2 = PhotoDetails.of(photoId2, "image2.png");
        PhotoDetails photoDetails3 = PhotoDetails.of(photoId3, "image3.png");
        photoDetailsRepository.saveAll(List.of(photoDetails1, photoDetails2, photoDetails3));

        Reaction reaction1 = Reaction.of(1L, postId1, POST, LIKE);
        Reaction reaction2 = Reaction.of(2L, postId2, POST, LIKE);
        reactionRepository.saveAll(List.of(reaction1, reaction2));

        //when
        List<PostResponse> recentPosts = postService.findRecentPosts(2);

        //then
        assertThat(recentPosts).hasSize(2)
                .extracting("id", "title", "content", "uploadFileName", "storeFileName", "likeCount")
                .containsExactlyInAnyOrder(
                        tuple(postId3, "hello3", "world3", "photoName3", "image3.png", 0L),
                        tuple(postId2, "hello2", "world2", "photoName2", "image2.png", 1L)
                );
    }

    @DisplayName("주어진 게시물 id보다 작은 게시물을 limit만큼 조회한다.")
    @Test
    void findAfter() {
        //given
        Post post1 = Post.of(1L, "hello1", "world1");
        Post post2 = Post.of(1L, "hello2", "world2");
        Post post3 = Post.of(2L, "hello3", "world3");
        postRepository.saveAll(List.of(post1, post2, post3));
        Long postId1 = post1.getId();
        Long postId2 = post2.getId();
        Long postId3 = post3.getId();

        Photo photo1 = Photo.of(postId1, "photoName1");
        Photo photo2 = Photo.of(postId2, "photoName2");
        Photo photo3 = Photo.of(postId3, "photoName3");
        photoRepository.saveAll(List.of(photo1, photo2, photo3));
        Long photoId1 = photo1.getId();
        Long photoId2 = photo2.getId();
        Long photoId3 = photo3.getId();

        PhotoDetails photoDetails1 = PhotoDetails.of(photoId1, "image1.png");
        PhotoDetails photoDetails2 = PhotoDetails.of(photoId2, "image2.png");
        PhotoDetails photoDetails3 = PhotoDetails.of(photoId3, "image3.png");
        photoDetailsRepository.saveAll(List.of(photoDetails1, photoDetails2, photoDetails3));

        Reaction reaction1 = Reaction.of(1L, postId1, POST, LIKE);
        Reaction reaction2 = Reaction.of(2L, postId2, POST, LIKE);
        reactionRepository.saveAll(List.of(reaction1, reaction2));

        PostCursorServiceRequest request = PostCursorServiceRequest.of(postId2, 5);

        //when
        List<PostResponse> posts = postService.findAfter(request);

        //then
        assertThat(posts).hasSize(1)
                .extracting("id", "title", "content", "uploadFileName", "storeFileName", "likeCount")
                .containsExactly(
                        tuple(postId1, "hello1", "world1", "photoName1", "image1.png", 1L)
                );
    }


    private MockMultipartFile getFile(String originalFileName) {
        return new MockMultipartFile("file", originalFileName, "image/jpeg", "Hello, World!".getBytes());
    }
}
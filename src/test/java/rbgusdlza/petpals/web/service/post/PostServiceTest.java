package rbgusdlza.petpals.web.service.post;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import rbgusdlza.petpals.domain.photo.Photo;
import rbgusdlza.petpals.domain.photo.PhotoDetails;
import rbgusdlza.petpals.domain.photo.PhotoDetailsRepository;
import rbgusdlza.petpals.domain.photo.PhotoRepository;
import rbgusdlza.petpals.domain.popularity.Popularity;
import rbgusdlza.petpals.domain.popularity.PopularityRepository;
import rbgusdlza.petpals.domain.post.Post;
import rbgusdlza.petpals.domain.post.PostRepository;
import rbgusdlza.petpals.domain.reaction.Reaction;
import rbgusdlza.petpals.domain.reaction.ReactionRepository;
import rbgusdlza.petpals.web.error.PetPalsException;
import rbgusdlza.petpals.web.service.popularity.PopularityService;
import rbgusdlza.petpals.web.service.post.request.PostCursorServiceRequest;
import rbgusdlza.petpals.web.service.post.request.PostRegisterServiceRequest;
import rbgusdlza.petpals.web.service.post.response.PostResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static rbgusdlza.petpals.domain.reaction.ReactionType.*;
import static rbgusdlza.petpals.domain.reaction.TargetType.POST;
import static rbgusdlza.petpals.web.error.ErrorCode.*;


@ActiveProfiles("test")
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PopularityService popularityService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private PhotoDetailsRepository photoDetailsRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private PopularityRepository popularityRepository;

    @AfterEach
    void tearDown() {
        photoDetailsRepository.deleteAllInBatch();
        photoRepository.deleteAllInBatch();
        postRepository.deleteAllInBatch();
        popularityRepository.deleteAllInBatch();
        reactionRepository.deleteAllInBatch();
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

    @DisplayName("게시물 아이디로 게시물을 조회한다.")
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

    @DisplayName("게시물 아이디로 게시물을 조회할 때, 게시물이 없으면 에러가 발생한다.")
    @Test
    void findByWithoutPost() {
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

        //when //then
        assertThatThrownBy(() -> postService.findBy(0L))
                .isInstanceOf(PetPalsException.class)
                .hasFieldOrPropertyWithValue("errorCode", POST_NOT_FOUND);
    }

    @DisplayName("최근 등록된 게시물을 주어진 수만큼 조회한다.")
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

    @DisplayName("게시물 아이디보다 작은 게시물을 주어진 수만큼 조회한다.")
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

    @DisplayName("인기 게시물을 주어진 수만큼 조회한다.")
    @Test
    void findPopularPosts() {
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

        Popularity popularity1 = Popularity.of(postId1, 0.5);
        Popularity popularity2 = Popularity.of(postId2, 1.0);
        Popularity popularity3 = Popularity.of(postId3, 0.1);
        popularityRepository.saveAll(List.of(popularity1, popularity2, popularity3));

        //when
        List<PostResponse> popularPosts = postService.findPopularPosts(2);

        //then
        assertThat(popularPosts).hasSize(2)
                .extracting("id", "title", "content", "uploadFileName", "storeFileName")
                .containsExactly(
                        tuple(postId2, "hello2", "world2", "photoName2", "image2.png"),
                        tuple(postId1, "hello1", "world1", "photoName1", "image1.png")
                );
    }

    @DisplayName("게시물 아이디로 게시물을 삭제한다.")
    @Test
    void remove() {
        //given
        Post post = Post.of(1L, "hello", "world");
        postRepository.save(post);
        Long postId = post.getId();

        Photo photo = Photo.of(postId, "photoName");
        photoRepository.save(photo);
        Long photoId = photo.getId();

        PhotoDetails photoDetails = PhotoDetails.of(photoId, "image.png");
        photoDetailsRepository.save(photoDetails);

        Popularity popularity = Popularity.of(postId, 0.5);
        popularityRepository.save(popularity);

        //when
        Long removedPostId = postService.remove(postId);

        //then
        assertAll(
                () -> assertThat(removedPostId).isEqualTo(postId),
                () -> assertThat(postRepository.findById(removedPostId)).isEmpty(),
                () -> assertThat(photoRepository.findByPostId(removedPostId)).isEmpty(),
                () -> assertThat(photoDetailsRepository.findByPhotoId(photoId)).isEmpty(),
                () -> assertThat(popularityRepository.findByPostId(removedPostId)).isEmpty()
        );
    }

    @DisplayName("사용자 아이디, 게시물 아이디로 사용자가 게시물을 등록했는지 확인한다.")
    @Test
    void isPostCreatedByMember() {
        //given
        Long memberId = 1L;
        Post post = Post.of(memberId, "hello", "world");
        postRepository.save(post);
        Long postId = post.getId();

        //when
        boolean result = postService.isPostCreatedByMember(postId, memberId);

        //then
        assertThat(result).isTrue();
    }

    private MockMultipartFile getFile(String originalFileName) {
        return new MockMultipartFile("file", originalFileName, "image/jpeg", "Hello, World!".getBytes());
    }
}
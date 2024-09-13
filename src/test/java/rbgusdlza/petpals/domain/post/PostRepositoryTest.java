package rbgusdlza.petpals.domain.post;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    void tearDown() {
        postRepository.deleteAllInBatch();
    }

    @DisplayName("Id 기준으로 내림차순된 게시물을 limit만큼 조회한다.")
    @Test
    void findAllByOrderByIdDesc() {
        //given
        Post post1 = Post.of(1L, "title1", "content1");
        Post post2 = Post.of(2L, "title2", "content2");
        Post post3 = Post.of(3L, "title3", "content3");
        Post post4 = Post.of(4L, "title4", "content4");
        Post post5 = Post.of(5L, "title5", "content5");
        postRepository.saveAll(List.of(post1, post2, post3, post4, post5));

        //when
        List<Post> findPosts = postRepository.findAllByOrderByIdDesc(2);

        //then
        assertThat(findPosts).hasSize(2)
                .extracting("title", "content")
                .containsExactlyInAnyOrder(
                        tuple("title5", "content5"),
                        tuple("title4", "content4")
                );
    }

    @DisplayName("주어진 Id보다 작은 Id 기준으로 내림차순된 게시물을 최대 limit만큼 조회한다.")
    @Test
    void findByIdLessThanOrderByIdDesc() {
        //given
        Post post1 = Post.of(1L, "title1", "content1");
        Post post2 = Post.of(2L, "title2", "content2");
        Post post3 = Post.of(3L, "title3", "content3");
        Post post4 = Post.of(4L, "title4", "content4");
        Post post5 = Post.of(5L, "title5", "content5");
        postRepository.saveAll(List.of(post1, post2, post3, post4, post5));

        //when
        List<Post> findPosts = postRepository.findAllByIdLessThanOrderByIdDesc(post3.getId(), 3);

        //then
        assertThat(findPosts).hasSize(2)
                .extracting("title", "content")
                .containsExactlyInAnyOrder(
                        tuple("title2", "content2"),
                        tuple("title1", "content1")
                );
    }

    @DisplayName("주어진 Id보다 작은 Id 기준으로 내림차순된 게시물을 limit만큼 조회할 때, 해당하는 게시물이 없으면 빈 리스트를 반환한다.")
    @Test
    void findByIdLessThanOrderByIdDesc2() {
        //given
        Post post1 = Post.of(1L, "title1", "content1");
        Post post2 = Post.of(2L, "title2", "content2");
        Post post3 = Post.of(3L, "title3", "content3");
        Post post4 = Post.of(4L, "title4", "content4");
        Post post5 = Post.of(5L, "title5", "content5");
        postRepository.saveAll(List.of(post1, post2, post3, post4, post5));

        //when
        List<Post> findPosts = postRepository.findAllByIdLessThanOrderByIdDesc(post1.getId(), 3);

        //then
        assertThat(findPosts).isEmpty();
    }
}
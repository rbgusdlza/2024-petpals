package rbgusdlza.petpals.web.service.post;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rbgusdlza.petpals.web.service.post.request.PostRegisterServiceRequest;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @DisplayName("게시물을 등록한다.")
    @Test
    void register() {
        //given
        PostRegisterServiceRequest request = PostRegisterServiceRequest.of(1L, "title", "content");

        //when
        Long postId = postService.register(request);

        //then
        assertThat(postId).isNotNull();
    }
}
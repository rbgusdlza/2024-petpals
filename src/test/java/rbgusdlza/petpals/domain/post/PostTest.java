package rbgusdlza.petpals.domain.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class PostTest {

    @DisplayName("게시물의 제목을 수정한다.")
    @Test
    void updateTitle() {
        //given
        Post post = Post.of(1L, "title1", "content");

        //when
        post.updateTitle("title2");

        //then
        assertThat(post.getTitle()).isEqualTo("title2");
    }

    @DisplayName("게시물의 내용을 수정한다.")
    @Test
    void updateContent() {
        //given
        Post post = Post.of(1L, "title", "content1");

        //when
        post.updateContent("content2");

        //then
        assertThat(post.getContent()).isEqualTo("content2");
    }
}
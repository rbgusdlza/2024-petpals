package rbgusdlza.petpals.web.controller.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import rbgusdlza.petpals.ControllerTestSupport;
import rbgusdlza.petpals.domain.photo.Photo;
import rbgusdlza.petpals.domain.photo.PhotoDetails;
import rbgusdlza.petpals.domain.photo.PhotoWithDetails;
import rbgusdlza.petpals.domain.post.Post;
import rbgusdlza.petpals.web.controller.post.request.PostRegisterRequest;
import rbgusdlza.petpals.web.service.post.response.PostResponse;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PostControllerTest extends ControllerTestSupport {

    @DisplayName("게시물을 등록하는 페이지를 보여준다.")
    @Test
    void register() throws Exception {
        mockMvc.perform(
                        get("/post/register")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("post/register"))
                .andExpect(model().attributeExists("postRegisterRequest"));
    }

    @DisplayName("게시물을 등록한다.")
    @Test
    void registerWithSuccess() throws Exception {
        //given
        PostRegisterRequest request = PostRegisterRequest.of(
                "title", "content", getFile("file")
        );

        //when //then
        mockMvc.perform(
                        post("/post/register")
                                .flashAttr("postRegisterRequest", request)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @DisplayName("게시물을 등록 시 유효하지 않은 입력일 경우, 다시 게시물을 등록하는 페이지를 보여준다.")
    @Test
    void registerWithFail() throws Exception {
        //given
        PostRegisterRequest request = PostRegisterRequest.of(
                "", "content", getFile("file")
        );

        //when //then
        mockMvc.perform(
                        post("/post/register")
                                .flashAttr("postRegisterRequest", request)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("post/register"))
                .andExpect(model().attributeHasFieldErrors("postRegisterRequest", "title"));
    }

    @DisplayName("주어진 수만큼 게시물을 보여준다.")
    @Test
    void list() throws Exception {
        mockMvc.perform(
                        get("/post/list?limit=5")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("post/list"))
                .andExpect(model().attributeExists("response"));
    }

    @DisplayName("해당 게시물을 보여준다.")
    @Test
    void show() throws Exception {
        //given
        PostResponse response = PostResponse.of(
                Post.of(1L, "title", "content"),
                PhotoWithDetails.of(Photo.of(1L, "photo"), PhotoDetails.of(1L, "photo")),
                1L
        );
        given(postService.findBy(1L)).willReturn(response);

        //when //then
        mockMvc.perform(
                        get("/post/list/{postId}", 1L)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("post/view"))
                .andExpect(model().attributeExists("response"));
    }

    private MockMultipartFile getFile(String originalFileName) {
        return new MockMultipartFile("file", originalFileName, "image/jpeg", "Hello, World!".getBytes());
    }
}
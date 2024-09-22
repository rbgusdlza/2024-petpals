package rbgusdlza.petpals.web.api.controller.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rbgusdlza.petpals.ControllerTestSupport;
import rbgusdlza.petpals.web.api.controller.post.request.PostCursorRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostApiControllerTest extends ControllerTestSupport {

    @DisplayName("최근 게시물을 주어진 수만큼 조회한다.")
    @Test
    void showLatest() throws Exception {
        mockMvc.perform(
                        get("/api/post/latest?limit=5")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("인기 게시물을 주어진 수만큼 조회한다.")
    @Test
    void showPopular() throws Exception {
        mockMvc.perform(
                        get("/api/post/popular?limit=5")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("게시물을 주어진 수만큼 추가로 조회한다.")
    @Test
    void list() throws Exception {
        //given
        PostCursorRequest request = PostCursorRequest.of(3L, 5);

        //when //then
        mockMvc.perform(
                        post("/api/post/list")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("현재 사용자가 게시물 삭제 권한이 있는지 확인한다.")
    @Test
    void checkAuth() throws Exception {
        mockMvc.perform(
                        get("/api/post/{postId}/auth-check", 1L)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("게시물을 삭제한다.")
    @Test
    void remove() throws Exception {
        mockMvc.perform(
                        delete("/api/post/{postId}", 1L)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }
}
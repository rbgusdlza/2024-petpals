package rbgusdlza.petpals.web.api.controller.reaction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rbgusdlza.petpals.ControllerTestSupport;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LikePopularApiControllerTest extends ControllerTestSupport {

    @DisplayName("인기 게시물 아이디로 좋아요를 생성한다.")
    @Test
    void like() throws Exception {
        mockMvc.perform(
                        put("/api/post/popular/{postId}/like", 1L)
                                .sessionAttr("id", 1L)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("게시물 아이디로 좋아요를 생성할 때, 로그인되어 있지 않으면 fail 응답을 반환한다.")
    @Test
    void likeWithFail() throws Exception {
        mockMvc.perform(
                        put("/api/post/popular/{postId}/like", 1L)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("로그인 상태에서만 좋아요가 가능합니다."));
    }

    @DisplayName("인기 게시물의 좋아요 개수를 조회한다.")
    @Test
    void countLike() throws Exception {
        mockMvc.perform(
                        get("/api/post/popular/{postId}/count-like", 1L)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }
}
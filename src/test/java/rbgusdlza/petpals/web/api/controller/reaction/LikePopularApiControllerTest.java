package rbgusdlza.petpals.web.api.controller.reaction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rbgusdlza.petpals.ControllerTestSupport;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LikePopularApiControllerTest extends ControllerTestSupport {

    @DisplayName("인기 게시물 아이디로 좋아요를 생성한다.")
    @Test
    void like() throws Exception {
        mockMvc.perform(
                        post("/api/post/popular/{postId}/like", 1L)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("인기 게시물 아이디로 좋아요를 생성한다.")
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
package rbgusdlza.petpals.web.api.controller.reaction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import rbgusdlza.petpals.global.messagebroker.MessageService;
import rbgusdlza.petpals.web.service.reaction.LikeService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LikeApiController.class)
class LikeApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LikeService likeService;

    @MockBean
    private MessageService messageService;

    @DisplayName("게시물 아이디로 좋아요를 생성한다.")
    @Test
    void like() throws Exception {
        mockMvc.perform(
                        post("/api/post/{postId}/like", 1L)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("게시물 아이디로 좋아요를 생성한다.")
    @Test
    void countLike() throws Exception {
        mockMvc.perform(
                        get("/api/post/{postId}/count-like", 1L)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }
}
package rbgusdlza.petpals;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import rbgusdlza.petpals.global.config.WebConfig;
import rbgusdlza.petpals.global.event.EmailSender;
import rbgusdlza.petpals.global.messagebroker.MessageService;
import rbgusdlza.petpals.web.api.controller.member.MemberApiController;
import rbgusdlza.petpals.web.api.controller.post.PostApiController;
import rbgusdlza.petpals.web.api.controller.reaction.LikeApiController;
import rbgusdlza.petpals.web.api.controller.reaction.LikePopularApiController;
import rbgusdlza.petpals.web.controller.member.MemberController;
import rbgusdlza.petpals.web.controller.post.PostController;
import rbgusdlza.petpals.web.service.member.MemberService;
import rbgusdlza.petpals.web.service.post.PostService;
import rbgusdlza.petpals.web.service.reaction.LikeCachedService;
import rbgusdlza.petpals.web.service.reaction.LikeService;

@WebMvcTest(controllers = {
        PostApiController.class,
        MemberApiController.class,
        LikeApiController.class,
        LikePopularApiController.class,
        MemberController.class,
        PostController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected EmailSender emailSender;

    @MockBean
    protected PostService postService;

    @MockBean
    protected LikeService likeService;

    @MockBean
    protected LikeCachedService likeCachedService;

    @MockBean
    protected MessageService messageService;

    @MockBean
    protected WebConfig webConfig;

}

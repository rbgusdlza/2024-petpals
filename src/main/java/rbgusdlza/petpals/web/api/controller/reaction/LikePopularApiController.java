package rbgusdlza.petpals.web.api.controller.reaction;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import rbgusdlza.petpals.global.messagebroker.MessageService;
import rbgusdlza.petpals.web.api.ApiResponse;
import rbgusdlza.petpals.web.service.reaction.LikeCachedService;

import static rbgusdlza.petpals.domain.reaction.TargetType.POST;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/post/popular")
@RestController
public class LikePopularApiController {

    private static final String EXCHANGE_NAME = "like.exchange";
    private static final String ROUTING_KEY = "like.routing";

    private final MessageService messageService;
    private final LikeCachedService likeCachedService;

    @PostMapping("/{postId}/like")
    public ApiResponse<Long> like(@PathVariable Long postId,
                                  HttpSession session) {
        messageService.sendMessage(EXCHANGE_NAME, ROUTING_KEY, postId);
        Long memberId = getMemberIdFrom(session);
        return ApiResponse.ok(likeCachedService.like(memberId, postId, POST));
    }

    @GetMapping("/{postId}/count-like")
    public ApiResponse<Long> countLike(@PathVariable Long postId) {
        return ApiResponse.ok(likeCachedService.countLike(postId, POST));
    }

    private Long getMemberIdFrom(HttpSession session) {
        return (Long) session.getAttribute("id");
    }
}

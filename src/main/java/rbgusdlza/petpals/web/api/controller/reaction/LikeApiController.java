package rbgusdlza.petpals.web.api.controller.reaction;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import rbgusdlza.petpals.global.messagebroker.MessageService;
import rbgusdlza.petpals.web.api.ApiResponse;
import rbgusdlza.petpals.web.service.reaction.LikeService;

import static rbgusdlza.petpals.domain.reaction.TargetType.POST;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/post")
@RestController
public class LikeApiController {

    private static final String EXCHANGE_NAME = "like.exchange";
    private static final String ROUTING_KEY = "like.routing";

    private final MessageService messageService;
    private final LikeService likeService;

    @PutMapping("/{postId}/like")
    public ApiResponse<Boolean> like(@PathVariable Long postId,
                                     HttpSession session) {
        if (doesUserLogin(session)) {
            return ApiResponse.fail("로그인 상태에서만 좋아요가 가능합니다.");
        }
        Long memberId = getMemberIdFrom(session);
        Boolean isLiked = likeService.like(memberId, postId, POST);
        messageService.sendMessage(EXCHANGE_NAME, ROUTING_KEY, postId);
        return ApiResponse.ok(isLiked);
    }

    @GetMapping("/{postId}/count-like")
    public ApiResponse<Long> countLike(@PathVariable Long postId) {
        return ApiResponse.ok(likeService.countLike(postId, POST));
    }

    private Long getMemberIdFrom(HttpSession session) {
        return (Long) session.getAttribute("id");
    }

    private boolean doesUserLogin(HttpSession session) {
        return session == null || session.getAttribute("id") == null;
    }
}

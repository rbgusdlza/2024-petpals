package rbgusdlza.petpals.web.controller.post;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rbgusdlza.petpals.web.controller.post.request.PostRegisterRequest;
import rbgusdlza.petpals.web.service.post.PostService;
import rbgusdlza.petpals.web.service.post.response.PostResponse;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post")
@Controller
public class PostController {

    private final PostService postService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("postRegisterRequest", new PostRegisterRequest());
        return "post/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute PostRegisterRequest request,
                           BindingResult bindingResult,
                           HttpSession session) {

        if (bindingResult.hasErrors()) {
            log.info("bindingResult : {}", bindingResult);
            return "post/register";
        }

        Long memberId = getMemberIdFrom(session);
        postService.register(request.toServiceRequest(memberId));
        return "redirect:/";
    }

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "5") int limit,
                       Model model) {
        List<PostResponse> posts = postService.findRecentPosts(limit);
        model.addAttribute("response", posts);
        return "post/list";
    }

    @GetMapping("/list/{postId}")
    public String show(@PathVariable Long postId,
                       Model model) {
        PostResponse response = postService.findBy(postId);
        model.addAttribute("response", response);
        return "post/view";
    }

    private Long getMemberIdFrom(HttpSession session) {
        return (Long) session.getAttribute("id");
    }
}

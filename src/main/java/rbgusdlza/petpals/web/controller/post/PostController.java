package rbgusdlza.petpals.web.controller.post;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rbgusdlza.petpals.web.controller.post.request.PostRegisterRequest;
import rbgusdlza.petpals.web.service.post.PostService;

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

    private Long getMemberIdFrom(HttpSession session) {
        return (Long) session.getAttribute("id");
    }
}

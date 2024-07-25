package rbgusdlza.petpals.web.controller.member;

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
import rbgusdlza.petpals.web.controller.member.request.MemberJoinRequest;
import rbgusdlza.petpals.web.controller.member.request.MemberLoginRequest;
import rbgusdlza.petpals.web.error.PetPalsException;
import rbgusdlza.petpals.web.service.member.MemberService;
import rbgusdlza.petpals.web.service.member.response.MemberResponse;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("memberJoinRequest", new MemberJoinRequest());
        return "member/join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute MemberJoinRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult : {}", bindingResult);
            return "member/join";
        }
        memberService.join(request.toServiceRequest());
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("memberLoginRequest", new MemberLoginRequest());
        return "member/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute MemberLoginRequest request,
                        BindingResult bindingResult,
                        HttpSession session) {

        if (bindingResult.hasErrors()) {
            log.info("bindingResult : {}", bindingResult);
            return "member/login";
        }

        try {
            MemberResponse response = memberService.login(request.toServiceRequest());
            session.setAttribute("id", response.getId());
            return "redirect:/";
        } catch (PetPalsException e) {
            log.error("Login failed: {}", e.getMessage());
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "member/login";
        }
    }
}


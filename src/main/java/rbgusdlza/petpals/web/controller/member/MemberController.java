package rbgusdlza.petpals.web.controller.member;

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
import rbgusdlza.petpals.web.service.member.MemberService;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("memberJoinRequest", new MemberJoinRequest());
        return "member/join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute MemberJoinRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult : {}", bindingResult);
            return "member/join";
        }
        memberService.joinMember(request.toServiceRequest());
        return "redirect:/";
    }
}


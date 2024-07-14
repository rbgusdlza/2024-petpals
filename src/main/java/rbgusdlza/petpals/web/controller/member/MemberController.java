package rbgusdlza.petpals.web.controller.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String join(@ModelAttribute MemberJoinRequest request) {
        memberService.joinMember(request.toServiceRequest());
        return "redirect:/";
    }
}


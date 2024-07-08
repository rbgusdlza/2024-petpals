package rbgusdlza.petpals.web.controller.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rbgusdlza.petpals.web.controller.member.request.MemberJoinRequest;

@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute MemberJoinRequest request, BindingResult bindingResult) {
        return "member/join";
    }
}


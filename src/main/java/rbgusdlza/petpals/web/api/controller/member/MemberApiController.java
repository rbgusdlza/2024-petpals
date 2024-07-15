package rbgusdlza.petpals.web.api.controller.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rbgusdlza.petpals.web.api.ApiResponse;
import rbgusdlza.petpals.web.api.controller.member.request.LoginIdForm;
import rbgusdlza.petpals.web.service.member.MemberService;
import rbgusdlza.petpals.web.service.member.response.LoginIdCheckResponse;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/check-login-id")
    public ApiResponse<LoginIdCheckResponse> checkLoginId(@Valid @RequestBody LoginIdForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResponse.of(HttpStatus.BAD_REQUEST, "Invalid Login ID", null);
        }
        return ApiResponse.ok(memberService.isLoginIdDuplicate(form.toServiceForm()));
    }
}

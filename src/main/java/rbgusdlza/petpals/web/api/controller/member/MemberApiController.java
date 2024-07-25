package rbgusdlza.petpals.web.api.controller.member;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rbgusdlza.petpals.global.event.EmailSender;
import rbgusdlza.petpals.global.util.RandomAuthCodeGenerator;
import rbgusdlza.petpals.web.api.ApiResponse;
import rbgusdlza.petpals.web.api.controller.member.request.EmailAuthCodeForm;
import rbgusdlza.petpals.web.api.controller.member.request.EmailForm;
import rbgusdlza.petpals.web.api.controller.member.request.LoginIdForm;
import rbgusdlza.petpals.web.api.controller.member.request.NicknameForm;
import rbgusdlza.petpals.web.service.member.MemberService;
import rbgusdlza.petpals.web.service.member.response.EmailCheckResponse;
import rbgusdlza.petpals.web.service.member.response.LoginIdCheckResponse;
import rbgusdlza.petpals.web.service.member.response.NicknameCheckResponse;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberApiController {

    public static final int AUTH_CODE_EXPIRATION_TIME = 300;

    private final MemberService memberService;
    private final EmailSender emailSender;

    @PostMapping("/login-id/check")
    public ApiResponse<LoginIdCheckResponse> checkLoginId(@Valid @RequestBody LoginIdForm form) {
        return ApiResponse.ok(memberService.isLoginIdDuplicate(form.toServiceForm()));
    }

    @PostMapping("/nickname/check")
    public ApiResponse<NicknameCheckResponse> checkNickname(@Valid @RequestBody NicknameForm form) {
        return ApiResponse.ok(memberService.isNicknameDuplicate(form.toServiceForm()));
    }

    @PostMapping("/email/check")
    public ApiResponse<EmailCheckResponse> checkEmail(@Valid @RequestBody EmailForm form) {
        return ApiResponse.ok(memberService.isEmailDuplicate(form.toServiceForm()));
    }

    @PostMapping("/auth-code/send")
    public ApiResponse<Void> sendAuthCode(@Valid @RequestBody EmailForm form, HttpSession session) {
        String authCode = RandomAuthCodeGenerator.getAuthCode();
        emailSender.sendAuthCode(form.getEmail(), authCode);
        saveAuthCodeInSession(session, authCode);
        return ApiResponse.ok();
    }

    @PostMapping("/auth-code/verify")
    public ApiResponse<Void> verifyAuthCode(@Valid @RequestBody EmailAuthCodeForm form, HttpSession session) {
        String sessionAuthCode = (String) session.getAttribute("authCode");
        if (sessionAuthCode == null || isInvalidAuthCode(form, sessionAuthCode)) {
            return ApiResponse.fail("Invalid Auth Code");
        }
        session.removeAttribute("authCode");
        return ApiResponse.ok();
    }

    private void saveAuthCodeInSession(HttpSession session, String authCode) {
        session.setAttribute("authCode", authCode);
        session.setMaxInactiveInterval(AUTH_CODE_EXPIRATION_TIME);
    }

    private boolean isInvalidAuthCode(EmailAuthCodeForm form, String sessionAuthCode) {
        return !sessionAuthCode.equals(form.getAuthCode());
    }
}

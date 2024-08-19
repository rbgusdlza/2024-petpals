package rbgusdlza.petpals.web.controller.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import rbgusdlza.petpals.domain.member.Member;
import rbgusdlza.petpals.web.controller.member.request.MemberJoinRequest;
import rbgusdlza.petpals.web.controller.member.request.MemberLoginRequest;
import rbgusdlza.petpals.web.error.PetPalsException;
import rbgusdlza.petpals.web.service.member.MemberService;
import rbgusdlza.petpals.web.service.member.response.MemberResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static rbgusdlza.petpals.web.error.ErrorCode.*;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @DisplayName("사용자에게 회원 가입 페이지를 보여준다")
    @Test
    void joinPage() throws Exception {
        mockMvc.perform(
                        get("/member/join"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/join"))
                .andExpect(model().attributeExists("memberJoinRequest"));
    }

    @DisplayName("회원을 등록한다.")
    @Test
    void joinWithSuccess() throws Exception {
        //given
        MemberJoinRequest request = MemberJoinRequest.of(
                "loginId", "nickname", "password", "email", true
        );

        //when //then
        mockMvc.perform(
                        post("/member/join")
                                .flashAttr("memberJoinRequest", request))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @DisplayName("회원 가입 시 유효하지 않은 입력일 경우, 다시 회원 가입 페이지를 보여준다.")
    @Test
    void joinWithError() throws Exception {
        //given
        MemberJoinRequest request = MemberJoinRequest.of(
                "", "nickname", "password", "", true
        );

        //when //then
        mockMvc.perform(
                    post("/member/join")
                            .flashAttr("memberJoinRequest", request))
                .andExpect(status().isOk())
                .andExpect(view().name("member/join"))
                .andExpect(model().attributeHasFieldErrors("memberJoinRequest", "loginId", "email"));
    }

    @DisplayName("사용자에게 로그인 페이지를 보여준다.")
    @Test
    void loginPage() throws Exception {
        mockMvc.perform(
                    get("/member/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/login"))
                .andExpect(model().attributeExists("memberLoginRequest"));
    }

    @DisplayName("사용자를 로그인 처리한다.")
    @Test
    void loginWithSuccess() throws Exception {
        //given
        MemberLoginRequest request = MemberLoginRequest.of("loginId", "password");
        Member member = Member.of("loginId", "nickname", "password", "email");
        MemberResponse response = MemberResponse.of(member);
        given(memberService.login(any())).willReturn(response);

        //when //then
        mockMvc.perform(
                    post("/member/login")
                        .flashAttr("memberLoginRequest", request))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @DisplayName("로그인 실패 시, 다시 로그인 페이지를 보여준다")
    @Test
    void loginWithFail() throws Exception {
        //given
        MemberLoginRequest request = MemberLoginRequest.of("loginId", "password");
        given(memberService.login(any())).willThrow(
                new PetPalsException(MEMBER_LOGIN_ERROR)
        );

        //when //then
        mockMvc.perform(
                    post("/member/login")
                        .flashAttr("memberLoginRequest", request))
                .andExpect(status().isOk())
                .andExpect(view().name("member/login"));
    }
}
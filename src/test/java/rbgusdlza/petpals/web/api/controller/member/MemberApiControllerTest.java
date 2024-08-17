package rbgusdlza.petpals.web.api.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import rbgusdlza.petpals.global.event.EmailSender;
import rbgusdlza.petpals.web.api.controller.member.request.EmailAuthCodeForm;
import rbgusdlza.petpals.web.api.controller.member.request.EmailForm;
import rbgusdlza.petpals.web.api.controller.member.request.LoginIdForm;
import rbgusdlza.petpals.web.api.controller.member.request.NicknameForm;
import rbgusdlza.petpals.web.service.member.MemberService;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberApiController.class)
class MemberApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @MockBean
    private EmailSender emailSender;

    @DisplayName("로그인 아이디가 중복되었는지 확인한다.")
    @Test
    void checkLoginId() throws Exception {
        //given
        LoginIdForm form = LoginIdForm.of("loginId");

        //when //then
        mockMvc.perform(
                    post("/api/member/login-id/check")
                            .content(objectMapper.writeValueAsString(form))
                            .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("닉네임이 중복되었는지 확인한다.")
    @Test
    void checkNickname() throws Exception {
        //given
        NicknameForm form = NicknameForm.of("nickname");

        //when //then
        mockMvc.perform(
                        post("/api/member/nickname/check")
                                .content(objectMapper.writeValueAsString(form))
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("이메일이 중복되었는지 확인한다.")
    @Test
    void checkEmail() throws Exception {
        //given
        EmailForm form = EmailForm.of("member@email.com");

        //when //then
        mockMvc.perform(
                        post("/api/member/email/check")
                                .content(objectMapper.writeValueAsString(form))
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("입력된 이메일로 인증 번호를 전송한다.")
    @Test
    void sendAuthCode() throws Exception {
        //given
        EmailForm form = EmailForm.of("member@email.com");

        //when //then
        mockMvc.perform(
                        post("/api/member/auth-code/send")
                                .content(objectMapper.writeValueAsString(form))
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("입력된 인증 번호가 일치하는지 확인한다.")
    @Test
    void verifyAuthCode() throws Exception {
        //given
        EmailAuthCodeForm form = EmailAuthCodeForm.of("member@email.com", "123456");

        //when //then
        mockMvc.perform(
                        post("/api/member/auth-code/verify")
                                .content(objectMapper.writeValueAsString(form))
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
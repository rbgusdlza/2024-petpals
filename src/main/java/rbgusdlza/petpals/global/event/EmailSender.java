package rbgusdlza.petpals.global.event.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import rbgusdlza.petpals.global.util.RandomAuthCodeGenerator;
import rbgusdlza.petpals.web.error.PetPalsException;

import static rbgusdlza.petpals.web.error.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class EmailSender {

    public static final String FROM_EMAIL = "rbgusdlza@gmail.com";
    private static final String TITLE = "[PETPALS] 인증메일입니다.";

    private final JavaMailSender javaMailSender;

    public void sendAuthCode(String toEmail, int authCode) {
        try {
            String content = getContentBy(authCode);
            MimeMessage message = getMessageOf(toEmail, content);
            javaMailSender.send(message);

        } catch (MessagingException e) {
            log.error("인증 이메일 전송 실패 : {}", e.getMessage());
            throw new PetPalsException(SENDING_EMAIL_ERROR);
        }
    }



    private MimeMessage getMessageOf(String email, String content) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom(FROM_EMAIL);
        messageHelper.setTo(email);
        messageHelper.setSubject(TITLE);
        messageHelper.setText(content);
        return message;
    }

    private String getContentBy(int autoCode) {
        return "인증번호는 " + autoCode + "입니다.";
    }
}

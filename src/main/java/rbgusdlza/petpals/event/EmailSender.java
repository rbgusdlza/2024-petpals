package rbgusdlza.petpals.event;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MailSender {

    private final JavaMailSender javaMailSender;


}

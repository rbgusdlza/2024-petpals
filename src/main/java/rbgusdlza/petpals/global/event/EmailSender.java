package rbgusdlza.petpals.global.event;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EmailSender {

    private final JavaMailSender javaMailSender;


}

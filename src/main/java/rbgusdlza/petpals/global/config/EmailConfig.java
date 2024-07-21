package rbgusdlza.petpals.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import rbgusdlza.petpals.global.event.EmailSender;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.protocol}")
    private String protocol;

    @Value("${spring.mail.default-encoding}")
    private String defaultEncoding;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean starttls;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth;

    @Bean
    public EmailSender emailSender(JavaMailSender javaMailSender) {
        return new EmailSender(javaMailSender);
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", starttls);
        properties.put("mail.smtp.auth", auth);

        javaMailSender.setProtocol(protocol);
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setDefaultEncoding(defaultEncoding);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setJavaMailProperties(properties);

        return javaMailSender;
    }
}

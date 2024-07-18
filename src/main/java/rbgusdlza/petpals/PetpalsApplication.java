package rbgusdlza.petpals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PetpalsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetpalsApplication.class, args);
    }

}

package tech.elsoft.wizercodechallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableJpaAuditing
public class WizerCodeChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(WizerCodeChallengeApplication.class, args);
    }

}

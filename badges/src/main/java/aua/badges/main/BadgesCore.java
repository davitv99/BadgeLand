package aua.badges.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoRepositories("aua.badges.repository")
@ComponentScan(basePackages = {"aua.badges"})
@EntityScan("aua.badges")
@EnableAsync
@EnableScheduling

public class BadgesCore {

    public static void main(String[] args) {
        SpringApplication.run(aua.badges.main.BadgesCore.class, args);
    }

}

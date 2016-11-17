package com.chubb.app;

import com.chubb.app.runner.TestRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

/**
 * This is main class for running testcases. Also this class loads Spring context and contains Spring configuration.
 * To run properly the main() method need to provide VM option (workDir) e.g. -DworkDir=C:\\work-directory .
 * <p>
 * Created by ichistruga on 10/11/2016.
 */
@SpringBootApplication
@ComponentScan("com.chubb")
//@Import(SpringConfig.class)
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);


    public static void main(String[] args) {
        System.out.println("==== Starting app ====");

        Config.init();

        SpringApplication app = new SpringApplication(Application.class);
        app.setAdditionalProfiles("production");
        app.run(args);
    }


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


    @Bean
    @Profile("production")
    public CommandLineRunner startPoint(TestRunner testRunner) {
        return (args) -> {
            testRunner.runAllTests();
            /*File journeyFile = FileUtils.getFile(Config.getJourneyFilesDir(), "jorney1.csv");
            if (!journeyFile.exists()) {
                throw new FileNotFoundException(journeyFile.getAbsolutePath());
            }
            testRunner.runTest(journeyFile);*/

        };
    }

}

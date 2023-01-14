package com.vesko;

import com.vesko.dynamicconfiguration.ConfigurationArgumentsReader;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@RequiredArgsConstructor
public class RequestCounterApplication implements ApplicationRunner {
    private final ConfigurationArgumentsReader configurationArgumentsReader;

    public static void main(String[] args) {
        new SpringApplicationBuilder(RequestCounterApplication.class)
                .run(args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        configurationArgumentsReader.read(args);
    }
}

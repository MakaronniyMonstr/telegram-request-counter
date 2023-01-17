package com.vesko.telegram.configuration;

import com.vesko.telegram.property.EchoMessageProperty;
import com.vesko.telegram.service.EchoMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
@EnableConfigurationProperties(EchoMessageProperty.class)
@RequiredArgsConstructor
public class EchoMessageConfiguration {
    private final EchoMessageProperty property;

    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(property.host())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Bean
    public EchoMessageService echoMessageService(Retrofit retrofit) {
        return retrofit.create(EchoMessageService.class);
    }
}

package com.mamta.gatewayserver.config;

import com.mamta.gatewayserver.service.CustomerSummaryClient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfig {


    @Value("${app.base.url}")
    private String baseUrl;

    @Bean
    CustomerSummaryClient customerClient(){
        WebClient webClient = WebClient.builder().baseUrl(baseUrl).build(); // reactive programming client

        WebClientAdapter webClientAdapter = WebClientAdapter.create(webClient);

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(webClientAdapter).build();
        // to generate code for actual code execution. [ proxy code ]
        return factory.createClient(CustomerSummaryClient.class);
    }
}

package com.mamta.customer;

import com.mamta.customer.interceptor.CommandMessageInterceptor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class CustomersApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomersApplication.class, args);
    }

    @Autowired
    public void registerCommandInterceptor(ApplicationContext applicationContext,
                                           CommandGateway commandGateway) {
        commandGateway.registerDispatchInterceptor(applicationContext.getBean(CommandMessageInterceptor.class));
    }

    // Registering processing group so that all the event handler [Query Side] can be processed
    @Autowired
    public void registerProcessingGroup(EventProcessingConfigurer configurer) {
        configurer.registerListenerInvocationErrorHandler("customer-group",
                conf-> PropagatingErrorHandler.instance());
    }
}

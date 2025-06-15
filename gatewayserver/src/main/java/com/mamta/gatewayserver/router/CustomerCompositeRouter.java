package com.mamta.gatewayserver.router;

import com.mamta.gatewayserver.handler.CustomerCompositeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class CustomerCompositeRouter {

    // This is how we define a rest api in reactive based programming
    @Bean
    public RouterFunction<ServerResponse> route(CustomerCompositeHandler handler) {
        return RouterFunctions.route(RequestPredicates.GET("/api/fetch/customers/")
//                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                        .and(RequestPredicates.queryParam("mobileNumber",p->true))
                , handler::fetchCustomerSummary);
    }
}

package com.mamta.gatewayserver.handler;

import com.mamta.gatewayserver.*;
import com.mamta.gatewayserver.service.CustomerSummaryClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerCompositeHandler {

    private final CustomerSummaryClient customerSummaryClient;

    public Mono<ServerResponse> fetchCustomerSummary(ServerRequest request) {

         String mobileNumber = request.queryParam("mobileNumber").get().split(" ")[0];

        log.info("Calling fetch API with mobile number: {}", mobileNumber);

         Mono<ResponseEntity<CustomerDto>> customerDetails = customerSummaryClient.fetchCustomerSummary(mobileNumber);
         Mono<ResponseEntity<AccountsDto>> accountsDetails = customerSummaryClient.fetchAccountsSummary(mobileNumber);
         Mono<ResponseEntity<LoansDto>> loanDetails = customerSummaryClient.fetchLoansSummary(mobileNumber);
         Mono<ResponseEntity<CardsDto>> cardDetails = customerSummaryClient.fetchCardsSummary(mobileNumber);

        return Mono.zip(customerDetails, accountsDetails, loanDetails, cardDetails)
                 .flatMap(tuple -> {
                     log.info("Calling fetch API with mobile number: {}", mobileNumber);

                     CustomerDto customerDto = tuple.getT1().getBody();
                     AccountsDto accountsDto = tuple.getT2().getBody();
                     LoansDto loansDto = tuple.getT3().getBody();
                     CardsDto cardsDto = tuple.getT4().getBody();

                     CustomerSummaryDto customerSummaryDto = new CustomerSummaryDto(
                             customerDto,accountsDto,loansDto,cardsDto
                     );
                     return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).
                             body(BodyInserters.fromValue(customerSummaryDto));
                 });
    }
}

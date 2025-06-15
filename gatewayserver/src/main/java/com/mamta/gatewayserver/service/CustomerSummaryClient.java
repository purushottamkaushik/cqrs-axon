package com.mamta.gatewayserver.service;

import com.mamta.gatewayserver.AccountsDto;
import com.mamta.gatewayserver.CardsDto;
import com.mamta.gatewayserver.CustomerDto;
import com.mamta.gatewayserver.LoansDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

public interface CustomerSummaryClient {


    @GetExchange(value = "/eazybank/customer/api/fetch" , accept = MediaType.APPLICATION_JSON_VALUE)
    Mono<ResponseEntity<CustomerDto>> fetchCustomerSummary(@RequestParam("mobileNumber") String mobileNumber);

    @GetExchange(value = "/eazybank/accounts/api/fetch" , accept = MediaType.APPLICATION_JSON_VALUE)
    Mono<ResponseEntity<AccountsDto>> fetchAccountsSummary(@RequestParam("mobileNumber") String mobileNumber);

    @GetExchange(value = "/eazybank/loans/api/fetch" , accept = MediaType.APPLICATION_JSON_VALUE)
    Mono<ResponseEntity<LoansDto>> fetchLoansSummary(@RequestParam("mobileNumber") String mobileNumber);

    @GetExchange(value = "/eazybank/cards/api/fetch" , accept = MediaType.APPLICATION_JSON_VALUE)
    Mono<ResponseEntity<CardsDto>> fetchCardsSummary(@RequestParam("mobileNumber") String mobileNumber);


}

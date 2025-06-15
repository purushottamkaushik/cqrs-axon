package com.mamta.customer.query;

import com.mamta.customer.dto.CustomerDto;
import com.mamta.customer.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerQueryHandler {

    private final ICustomerService customerService;

    @QueryHandler
    public CustomerDto getCustomerById(FindCustomerQuery findCustomerQuery) {
        log.info("Get customer by mobile number: {}", findCustomerQuery.getMobileNumber());
      return   customerService.fetchCustomer(findCustomerQuery.getMobileNumber());
    }
}

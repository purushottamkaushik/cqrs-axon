package com.mamta.customer.query;

import com.mamta.customer.dto.CustomerDto;
import com.mamta.customer.repository.CustomerRepository;
import com.mamta.customer.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerQueryHandler {

    private final ICustomerService customerService;

    @QueryHandler
    public CustomerDto getCustomerById(String mobileNumber) {
      return   customerService.fetchCustomer(mobileNumber);
    }
}

package com.mamta.customer.query;

import com.mamta.customer.dto.CustomerDto;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@RequiredArgsConstructor
@Slf4j
public class CustomerQueryController {


    private final QueryGateway queryGateway;
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchCustomerDetails(@RequestParam("mobileNumber")
                                                            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                            String mobileNumber) {
        log.info("Fetching customer details using fetchCustomerDetails for {}", mobileNumber);
        FindCustomerQuery  query = new FindCustomerQuery(mobileNumber);
        CustomerDto customerResponse = queryGateway.query(query, ResponseTypes.instanceOf(CustomerDto.class)).join();
        log.info("Fetched customer details for {}", customerResponse);
        return ResponseEntity.status(org.springframework.http.HttpStatus.OK).body(customerResponse);
    }
}

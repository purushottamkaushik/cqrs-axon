package com.mamta.customer.command.controller;

import com.mamta.customer.command.CreateCustomerCommand;
import com.mamta.customer.command.UpdateCustomerCommand;
import com.mamta.customer.constants.CustomerConstants;
import com.mamta.customer.dto.CustomerDto;
import com.mamta.customer.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@RequiredArgsConstructor
public class CustomerCommandController {


    private final CommandGateway commandGateway;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCustomer(@Valid @RequestBody
                                                          CustomerDto customerDto) {
        CreateCustomerCommand createCustomerCommand = CreateCustomerCommand.builder()
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .activeSw(true)
                .build();

        customerDto.setCustomerId(UUID.randomUUID().toString());
//        iCustomerService.createCustomer(customerDto);
        commandGateway.sendAndWait(createCustomerCommand);
        return ResponseEntity
                .status(org.springframework.http.HttpStatus.CREATED)
                .body(new ResponseDto(CustomerConstants.STATUS_201, CustomerConstants.MESSAGE_201));
    }


    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCustomerDetails(@Valid @RequestBody CustomerDto customerDto) {
        UpdateCustomerCommand updateCustomerCommand = UpdateCustomerCommand.builder()
                .name(customerDto.getName())
                .customerId(customerDto.getCustomerId())
                .email(customerDto.getEmail())
                .activeSw(true)
                .build();
        commandGateway.sendAndWait(updateCustomerCommand);
        return ResponseEntity
                .status(org.springframework.http.HttpStatus.OK)
                .body(new ResponseDto(CustomerConstants.STATUS_200, CustomerConstants.MESSAGE_200));
    }
}

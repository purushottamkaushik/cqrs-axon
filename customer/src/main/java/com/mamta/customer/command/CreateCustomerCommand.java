package com.mamta.customer.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateCustomerCommand {


    @TargetAggregateIdentifier
    private String customerId;
    private String name;
    private String email;
    private String mobileNumber;
    private Boolean activeSw;
}

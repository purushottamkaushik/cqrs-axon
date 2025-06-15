package com.mamta.customer.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class DeleteCustomerCommand {


    @TargetAggregateIdentifier
    private String customerId;

    private Boolean activeSw;
}

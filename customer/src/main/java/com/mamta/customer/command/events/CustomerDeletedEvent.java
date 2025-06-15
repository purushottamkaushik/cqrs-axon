package com.mamta.customer.command.events;

import lombok.Data;

@Data
public class CustomerDeletedEvent {

    private String customerId;
    private Boolean activeSw;
}

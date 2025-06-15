package com.mamta.customer.command.events;

import lombok.Data;

@Data
public class CustomerUpdatedEvent {

    private String customerId;
    private String name;
    private String email;
    private String mobileNumber;
    private Boolean activeSw;
}

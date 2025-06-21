package com.mamta.customer.query;

import com.mamta.customer.command.events.CustomerCreatedEvent;
import com.mamta.customer.command.events.CustomerDeletedEvent;
import com.mamta.customer.command.events.CustomerUpdatedEvent;
import com.mamta.customer.entity.Customer;

import com.mamta.customer.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("customer-group")
public class CustomerProjection {

    private final ICustomerService customerService;

    @EventHandler
    public void on(CustomerCreatedEvent event) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(event, customer);
        customerService.createCustomer(customer);
    }

    @EventHandler
    public void on(CustomerUpdatedEvent event) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(event, customer);
        customerService.updateCustomer(customer);
    }

    @EventHandler
    public void on(CustomerDeletedEvent event) {
        customerService.deleteCustomer(event.getCustomerId());
    }

}

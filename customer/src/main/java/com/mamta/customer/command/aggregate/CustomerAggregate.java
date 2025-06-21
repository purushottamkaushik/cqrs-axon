package com.mamta.customer.command.aggregate;

import com.mamta.customer.command.CreateCustomerCommand;
import com.mamta.customer.command.DeleteCustomerCommand;
import com.mamta.customer.command.UpdateCustomerCommand;
import com.mamta.customer.command.events.CustomerCreatedEvent;
import com.mamta.customer.command.events.CustomerDeletedEvent;
import com.mamta.customer.command.events.CustomerUpdatedEvent;
import com.mamta.customer.exception.ResourceNotFoundException;
import com.mamta.customer.repository.CustomerRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Slf4j
@Aggregate
@Data
public class CustomerAggregate {

    @AggregateIdentifier
    private String customerId;
    private String name;
    private String email;
    private String mobileNumber;
    private Boolean activeSw;


    public CustomerAggregate() {}

    /*
    For create operations we use constructor and pass command as an arguement
    and for other operations we must use a normal method
     */
    @CommandHandler
    public CustomerAggregate(CreateCustomerCommand createCustomerCommand, CustomerRepository customerRepository) {


//        In this we are processing command into event and send it to
//         Optional<Customer> customerOptional = customerRepository.findByMobileNumberAndActiveSw(createCustomerCommand.getMobileNumber(),
//                createCustomerCommand.getActiveSw());
//         if (customerOptional.isPresent()) {
//             throw  new CustomerAlreadyExistsException("Customer already exists with mobileNumber: " +
//                     createCustomerCommand.getMobileNumber() );
//         }
        CustomerCreatedEvent customerCreatedEvent = new CustomerCreatedEvent();
         BeanUtils.copyProperties(createCustomerCommand, customerCreatedEvent);
        AggregateLifecycle.apply(customerCreatedEvent); // send to event sourcing handler

    }

    @EventSourcingHandler
    public void on(CustomerCreatedEvent event) {
        log.info("Customer created event: {}", event);
        // Using this events will be published to the event sourcing db in axon
        this.customerId = event.getCustomerId();
        this.name = event.getName();
        this.email = event.getEmail();
        this.mobileNumber = event.getMobileNumber();
        this.activeSw = event.getActiveSw();

    }

    @CommandHandler
    public void updateAggregate(UpdateCustomerCommand updateCustomerCommand, EventStore eventStore) {
        // Read all the events from the axon event store
        List<?> eventMessages = eventStore.readEvents(updateCustomerCommand.getCustomerId()).asStream().toList();
        // if no event found throw exception else continue copying data from command to event and publish
        if (eventMessages.isEmpty()) {
            throw new ResourceNotFoundException("Customer", "mobileNumber", updateCustomerCommand.getMobileNumber());
        }

        CustomerUpdatedEvent customerUpdatedEvent = new CustomerUpdatedEvent();
        BeanUtils.copyProperties(updateCustomerCommand, customerUpdatedEvent);
        AggregateLifecycle.apply(customerUpdatedEvent);
    }

    @EventSourcingHandler
    public void onUpdate(UpdateCustomerCommand updateCustomerCommand) {
        this.mobileNumber = updateCustomerCommand.getMobileNumber();
        this.activeSw = updateCustomerCommand.getActiveSw();
    }
    @CommandHandler
    public void deleteAggregate(DeleteCustomerCommand deleteCustomerCommand) {
        CustomerDeletedEvent customerDeletedEvent = new CustomerDeletedEvent();
        BeanUtils.copyProperties(deleteCustomerCommand, customerDeletedEvent);
        AggregateLifecycle.apply(customerDeletedEvent);
    }

    @EventSourcingHandler
    public void onDelete(DeleteCustomerCommand deleteCustomerCommand ,
                                CustomerRepository customerRepository
    ) {
        this.customerId = deleteCustomerCommand.getCustomerId();
        this.activeSw = deleteCustomerCommand.getActiveSw();
    }


}

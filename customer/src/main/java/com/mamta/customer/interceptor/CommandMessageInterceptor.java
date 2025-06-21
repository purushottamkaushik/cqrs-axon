package com.mamta.customer.interceptor;

import com.mamta.customer.command.CreateCustomerCommand;
import com.mamta.customer.command.DeleteCustomerCommand;
import com.mamta.customer.command.UpdateCustomerCommand;
import com.mamta.customer.entity.Customer;
import com.mamta.customer.exception.CustomerAlreadyExistsException;
import com.mamta.customer.exception.ResourceNotFoundException;
import com.mamta.customer.repository.CustomerRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Component
public class CommandMessageInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final CustomerRepository customerRepository;

    public CommandMessageInterceptor(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {

        return (index, command) -> {
            if (CreateCustomerCommand.class.equals(command.getPayloadType())) {
                CreateCustomerCommand createCustomerCommand = (CreateCustomerCommand) command.getPayload();
                Optional<Customer> customerOptional = customerRepository.findByMobileNumberAndActiveSw(createCustomerCommand.getMobileNumber(),
                        createCustomerCommand.getActiveSw());
                if (customerOptional.isPresent()) {
                    throw new CustomerAlreadyExistsException("Customer already exists with mobileNumber: " +
                            createCustomerCommand.getMobileNumber());
                }
            } else if (UpdateCustomerCommand.class.equals(command.getPayloadType())) {
                UpdateCustomerCommand updateCustomerCommand = (UpdateCustomerCommand) command.getPayload();
                Customer customer = customerRepository.findByMobileNumberAndActiveSw(updateCustomerCommand.getMobileNumber(), true)
                        .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", updateCustomerCommand.getMobileNumber()));

            } else if (DeleteCustomerCommand.class.equals(command.getPayloadType())) {
                DeleteCustomerCommand deleteCustomerCommand = (DeleteCustomerCommand) command.getPayload();
                customerRepository.findByCustomerIdAndActiveSw(deleteCustomerCommand.getCustomerId(), true)
                        .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", deleteCustomerCommand.getCustomerId()));
            }
            return command;
        };


    }
}

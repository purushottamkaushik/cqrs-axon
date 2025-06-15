package com.mamta.customer.service.impl;

import com.mamta.customer.constants.CustomerConstants;
import com.mamta.customer.dto.CustomerDto;
import com.mamta.customer.entity.Customer;
import com.mamta.customer.exception.CustomerAlreadyExistsException;
import com.mamta.customer.exception.ResourceNotFoundException;
import com.mamta.customer.mapper.CustomerMapper;
import com.mamta.customer.repository.CustomerRepository;
import com.mamta.customer.service.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private CustomerRepository customerRepository;

    @Override
    public void createCustomer(Customer customer) {


        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumberAndActiveSw(
                customer.getMobileNumber(), true);
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    + customer.getMobileNumber());
        }
        customerRepository.save(customer);
    }

    @Override
    public CustomerDto fetchCustomer(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumberAndActiveSw(mobileNumber, true).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        return CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        String mobileNumber = customer.getMobileNumber();
         customer = customerRepository.findByMobileNumberAndActiveSw(customer.getMobileNumber(), true)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        customerRepository.save(customer);
        return true;
    }

    @Override
    public boolean deleteCustomer(String customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "customerId", customerId)
        );
        customer.setActiveSw(CustomerConstants.IN_ACTIVE_SW);
        customerRepository.save(customer);
        return true;
    }

}

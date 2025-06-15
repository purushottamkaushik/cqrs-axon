package com.mamta.customer.query;

import lombok.Value;

@Value
public class FindCustomerQuery {

    private String mobileNumber;

    public FindCustomerQuery(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}

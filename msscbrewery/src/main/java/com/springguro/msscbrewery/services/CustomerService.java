package com.springguro.msscbrewery.services;

import java.util.UUID;

import com.springguro.msscbrewery.web.model.CustomerDto;

public interface CustomerService {
    CustomerDto getCustomerById(UUID customerId);
}

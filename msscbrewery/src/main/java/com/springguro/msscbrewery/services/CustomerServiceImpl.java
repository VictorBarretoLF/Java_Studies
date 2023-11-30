package com.springguro.msscbrewery.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.springguro.msscbrewery.web.model.CustomerDto;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerDto getCustomerById(UUID customerId) {
        return CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("Joe Buck")
                .build();
    }
}

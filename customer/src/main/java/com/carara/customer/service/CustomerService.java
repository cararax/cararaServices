package com.carara.customer.service;

import com.carara.customer.model.Customer;
import com.carara.customer.model.request.CustomerRegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public record CustomerService() {
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        //todo: check if email is valid
        //todo: check if email is not taken
        //todo: store customer in db


    }
}
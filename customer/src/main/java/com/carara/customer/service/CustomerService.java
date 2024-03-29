package com.carara.customer.service;

import com.carara.amqp.RabbitMQMessageProducer;
import com.carara.clients.fraud.FraudClient;
import com.carara.clients.fraud.response.FraudCheckResponse;
import com.carara.clients.notification.NotificationClient;
import com.carara.clients.notification.request.NotificationRequest;
import com.carara.customer.model.Customer;
import com.carara.customer.model.request.CustomerRegistrationRequest;
import com.carara.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(
        CustomerRepository customerRepository, FraudClient fraudClient,
        RabbitMQMessageProducer rabbitMQMessageProducer
) {
    public void registerCustomer(CustomerRegistrationRequest request) {

        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        //todo: check if email is valid
        //todo: check if email is not taken

        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        assert fraudCheckResponse != null;
        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("Fraudster");
        }

        customerRepository.save(customer);

        String message = String.format("Hi, %s, welcome to CararaSerivce!", customer.getFirstName());
        NotificationRequest notificationRequest = new NotificationRequest(customer.getId(), customer.getEmail(), message);

        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );
    }
}

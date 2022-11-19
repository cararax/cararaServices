package com.carara.customer.model.request;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email
) {
}

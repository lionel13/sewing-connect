package com.sewingconnect.domain.model;

import com.sewingconnect.domain.DomainException;

public record PersonName(String value) {

    public PersonName {
        if (value == null || value.isBlank()) {
            throw new DomainException("Person name must not be blank.");
        }
        value = value.trim();
    }
}

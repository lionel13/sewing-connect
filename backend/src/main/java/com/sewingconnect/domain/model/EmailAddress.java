package com.sewingconnect.domain.model;

import com.sewingconnect.domain.DomainException;

import java.util.Locale;

public record EmailAddress(String value) {

    public EmailAddress {
        if (value == null || value.isBlank()) {
            throw new DomainException("Email address must not be blank.");
        }
        String normalized = value.trim().toLowerCase(Locale.ROOT);
        if (!normalized.contains("@") || normalized.startsWith("@") || normalized.endsWith("@")) {
            throw new DomainException("Email address must contain a local part and a domain.");
        }
        value = normalized;
    }
}

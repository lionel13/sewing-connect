package com.sewingconnect.domain.model;

import java.util.Objects;

public record Student(StudentId id, PersonName name, EmailAddress emailAddress) {

    public Student {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(emailAddress, "emailAddress must not be null");
    }
}

package com.sewingconnect.domain.model;

import java.util.Objects;

public record Teacher(TeacherId id, PersonName name, EmailAddress emailAddress) {

    public Teacher {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(emailAddress, "emailAddress must not be null");
    }
}

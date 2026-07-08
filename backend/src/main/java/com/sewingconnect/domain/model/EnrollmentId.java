package com.sewingconnect.domain.model;

import java.util.Objects;
import java.util.UUID;

public record EnrollmentId(UUID value) {

    public EnrollmentId {
        Objects.requireNonNull(value, "value must not be null");
    }

    public static EnrollmentId newId() {
        return new EnrollmentId(UUID.randomUUID());
    }
}

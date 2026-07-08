package com.sewingconnect.domain.model;

import java.util.Objects;
import java.util.UUID;

public record TeacherId(UUID value) {

    public TeacherId {
        Objects.requireNonNull(value, "value must not be null");
    }

    public static TeacherId newId() {
        return new TeacherId(UUID.randomUUID());
    }
}

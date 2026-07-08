package com.sewingconnect.domain.model;

import java.util.Objects;
import java.util.UUID;

public record CourseSessionId(UUID value) {

    public CourseSessionId {
        Objects.requireNonNull(value, "value must not be null");
    }

    public static CourseSessionId newId() {
        return new CourseSessionId(UUID.randomUUID());
    }
}

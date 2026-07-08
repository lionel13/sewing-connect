package com.sewingconnect.domain.model;

import java.util.Objects;
import java.util.UUID;

public record SewingCourseId(UUID value) {

    public SewingCourseId {
        Objects.requireNonNull(value, "value must not be null");
    }

    public static SewingCourseId newId() {
        return new SewingCourseId(UUID.randomUUID());
    }
}

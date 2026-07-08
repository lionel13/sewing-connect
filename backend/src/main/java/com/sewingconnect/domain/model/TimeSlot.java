package com.sewingconnect.domain.model;

import com.sewingconnect.domain.DomainException;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public record TimeSlot(Instant startsAt, Duration duration) {

    public TimeSlot {
        Objects.requireNonNull(startsAt, "startsAt must not be null");
        Objects.requireNonNull(duration, "duration must not be null");
        if (duration.isZero() || duration.isNegative()) {
            throw new DomainException("Time slot duration must be positive.");
        }
    }

    public Instant endsAt() {
        return startsAt.plus(duration);
    }

    public boolean overlaps(TimeSlot other) {
        Objects.requireNonNull(other, "other must not be null");
        return startsAt.isBefore(other.endsAt()) && other.startsAt().isBefore(endsAt());
    }
}

package com.sewingconnect.domain.model;

import com.sewingconnect.domain.DomainException;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TimeSlotTest {

    @Test
    void computesEndTime() {
        TimeSlot timeSlot = new TimeSlot(Instant.parse("2026-09-01T10:00:00Z"), Duration.ofMinutes(90));

        assertThat(timeSlot.endsAt()).isEqualTo(Instant.parse("2026-09-01T11:30:00Z"));
    }

    @Test
    void detectsOverlappingSlots() {
        TimeSlot morning = new TimeSlot(Instant.parse("2026-09-01T10:00:00Z"), Duration.ofHours(2));
        TimeSlot overlapping = new TimeSlot(Instant.parse("2026-09-01T11:30:00Z"), Duration.ofHours(2));
        TimeSlot afternoon = new TimeSlot(Instant.parse("2026-09-01T13:00:00Z"), Duration.ofHours(2));

        assertThat(morning.overlaps(overlapping)).isTrue();
        assertThat(morning.overlaps(afternoon)).isFalse();
    }

    @Test
    void rejectsNonPositiveDuration() {
        assertThatThrownBy(() -> new TimeSlot(Instant.parse("2026-09-01T10:00:00Z"), Duration.ZERO))
            .isInstanceOf(DomainException.class)
            .hasMessage("Time slot duration must be positive.");
    }
}

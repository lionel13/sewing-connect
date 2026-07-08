package com.sewingconnect.domain.model;

import com.sewingconnect.domain.DomainException;

import java.time.Duration;
import java.util.Objects;

public record SewingCourse(
    SewingCourseId id,
    String title,
    String description,
    CourseLevel level,
    int capacity,
    Duration defaultDuration
) {

    public SewingCourse {
        Objects.requireNonNull(id, "id must not be null");
        if (title == null || title.isBlank()) {
            throw new DomainException("Course title must not be blank.");
        }
        Objects.requireNonNull(level, "level must not be null");
        Objects.requireNonNull(defaultDuration, "defaultDuration must not be null");
        if (capacity < 1) {
            throw new DomainException("Course capacity must be positive.");
        }
        if (defaultDuration.isZero() || defaultDuration.isNegative()) {
            throw new DomainException("Course duration must be positive.");
        }
        title = title.trim();
        description = description == null ? "" : description.trim();
    }

    public CourseSession schedule(CourseSessionId sessionId, TeacherId teacherId, TimeSlot timeSlot) {
        return new CourseSession(sessionId, id, teacherId, timeSlot, capacity);
    }
}

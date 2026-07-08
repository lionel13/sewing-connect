package com.sewingconnect.domain.model;

import com.sewingconnect.domain.DomainException;

import java.time.Instant;
import java.util.Objects;

public record Enrollment(
    EnrollmentId id,
    StudentId studentId,
    EnrollmentStatus status,
    Instant enrolledAt,
    Instant cancelledAt
) {

    public Enrollment {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(studentId, "studentId must not be null");
        Objects.requireNonNull(status, "status must not be null");
        Objects.requireNonNull(enrolledAt, "enrolledAt must not be null");
        if (status == EnrollmentStatus.CANCELLED && cancelledAt == null) {
            throw new DomainException("A cancelled enrollment must have a cancellation time.");
        }
        if (status == EnrollmentStatus.ACTIVE && cancelledAt != null) {
            throw new DomainException("An active enrollment cannot have a cancellation time.");
        }
    }

    public static Enrollment active(EnrollmentId id, StudentId studentId, Instant enrolledAt) {
        return new Enrollment(id, studentId, EnrollmentStatus.ACTIVE, enrolledAt, null);
    }

    public Enrollment cancel(Instant cancelledAt) {
        if (status == EnrollmentStatus.CANCELLED) {
            throw new DomainException("Enrollment is already cancelled.");
        }
        return new Enrollment(id, studentId, EnrollmentStatus.CANCELLED, enrolledAt, cancelledAt);
    }
}

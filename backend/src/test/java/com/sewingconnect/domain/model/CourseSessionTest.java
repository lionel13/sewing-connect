package com.sewingconnect.domain.model;

import com.sewingconnect.domain.DomainException;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CourseSessionTest {

    private final CourseSession session = new CourseSession(
        CourseSessionId.newId(),
        SewingCourseId.newId(),
        TeacherId.newId(),
        new TimeSlot(Instant.parse("2026-09-01T10:00:00Z"), Duration.ofHours(2)),
        2
    );

    @Test
    void enrollsStudentsUntilCapacityIsReached() {
        StudentId firstStudent = StudentId.newId();
        StudentId secondStudent = StudentId.newId();

        session.enroll(EnrollmentId.newId(), firstStudent, Instant.parse("2026-08-01T10:00:00Z"));
        session.enroll(EnrollmentId.newId(), secondStudent, Instant.parse("2026-08-01T10:05:00Z"));

        assertThat(session.activeEnrollmentCount()).isEqualTo(2);
        assertThat(session.isFull()).isTrue();
    }

    @Test
    void rejectsDuplicateEnrollmentForSameStudent() {
        StudentId student = StudentId.newId();
        session.enroll(EnrollmentId.newId(), student, Instant.parse("2026-08-01T10:00:00Z"));

        assertThatThrownBy(() -> session.enroll(EnrollmentId.newId(), student, Instant.parse("2026-08-01T10:05:00Z")))
            .isInstanceOf(DomainException.class)
            .hasMessage("Student is already enrolled in this session.");
    }

    @Test
    void rejectsEnrollmentWhenSessionIsFull() {
        session.enroll(EnrollmentId.newId(), StudentId.newId(), Instant.parse("2026-08-01T10:00:00Z"));
        session.enroll(EnrollmentId.newId(), StudentId.newId(), Instant.parse("2026-08-01T10:05:00Z"));

        assertThatThrownBy(() -> session.enroll(EnrollmentId.newId(), StudentId.newId(), Instant.parse("2026-08-01T10:10:00Z")))
            .isInstanceOf(DomainException.class)
            .hasMessage("Course session is full.");
    }

    @Test
    void freesCapacityWhenEnrollmentIsCancelled() {
        StudentId student = StudentId.newId();
        session.enroll(EnrollmentId.newId(), student, Instant.parse("2026-08-01T10:00:00Z"));

        Enrollment cancelled = session.cancelEnrollment(student, Instant.parse("2026-08-02T10:00:00Z"));

        assertThat(cancelled.status()).isEqualTo(EnrollmentStatus.CANCELLED);
        assertThat(session.activeEnrollmentCount()).isZero();
    }

    @Test
    void rejectsEnrollmentWhenSessionIsCancelled() {
        session.cancel();

        assertThatThrownBy(() -> session.enroll(EnrollmentId.newId(), StudentId.newId(), Instant.parse("2026-08-01T10:00:00Z")))
            .isInstanceOf(DomainException.class)
            .hasMessage("Course session is not scheduled.");
    }
}

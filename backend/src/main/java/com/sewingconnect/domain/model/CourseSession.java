package com.sewingconnect.domain.model;

import com.sewingconnect.domain.DomainException;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class CourseSession {

    private final CourseSessionId id;
    private final SewingCourseId courseId;
    private final TeacherId teacherId;
    private final TimeSlot timeSlot;
    private final int capacity;
    private final Map<StudentId, Enrollment> enrollments = new LinkedHashMap<>();
    private SessionStatus status = SessionStatus.SCHEDULED;

    public CourseSession(
        CourseSessionId id,
        SewingCourseId courseId,
        TeacherId teacherId,
        TimeSlot timeSlot,
        int capacity
    ) {
        if (capacity < 1) {
            throw new DomainException("A course session must accept at least one student.");
        }
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.courseId = Objects.requireNonNull(courseId, "courseId must not be null");
        this.teacherId = Objects.requireNonNull(teacherId, "teacherId must not be null");
        this.timeSlot = Objects.requireNonNull(timeSlot, "timeSlot must not be null");
        this.capacity = capacity;
    }

    public Enrollment enroll(EnrollmentId enrollmentId, StudentId studentId, Instant enrolledAt) {
        ensureScheduled();
        Objects.requireNonNull(studentId, "studentId must not be null");
        if (enrollments.containsKey(studentId)) {
            throw new DomainException("Student is already enrolled in this session.");
        }
        if (isFull()) {
            throw new DomainException("Course session is full.");
        }
        Enrollment enrollment = Enrollment.active(enrollmentId, studentId, enrolledAt);
        enrollments.put(studentId, enrollment);
        return enrollment;
    }

    public Enrollment cancelEnrollment(StudentId studentId, Instant cancelledAt) {
        Objects.requireNonNull(studentId, "studentId must not be null");
        Enrollment enrollment = enrollments.get(studentId);
        if (enrollment == null) {
            throw new DomainException("Student is not enrolled in this session.");
        }
        Enrollment cancelled = enrollment.cancel(cancelledAt);
        enrollments.put(studentId, cancelled);
        return cancelled;
    }

    public void cancel() {
        ensureScheduled();
        status = SessionStatus.CANCELLED;
    }

    public boolean isFull() {
        return activeEnrollmentCount() >= capacity;
    }

    public int activeEnrollmentCount() {
        return (int) enrollments.values().stream()
            .filter(enrollment -> enrollment.status() == EnrollmentStatus.ACTIVE)
            .count();
    }

    public Collection<Enrollment> enrollments() {
        return Collections.unmodifiableCollection(enrollments.values());
    }

    public CourseSessionId id() {
        return id;
    }

    public SewingCourseId courseId() {
        return courseId;
    }

    public TeacherId teacherId() {
        return teacherId;
    }

    public TimeSlot timeSlot() {
        return timeSlot;
    }

    public int capacity() {
        return capacity;
    }

    public SessionStatus status() {
        return status;
    }

    private void ensureScheduled() {
        if (status != SessionStatus.SCHEDULED) {
            throw new DomainException("Course session is not scheduled.");
        }
    }
}

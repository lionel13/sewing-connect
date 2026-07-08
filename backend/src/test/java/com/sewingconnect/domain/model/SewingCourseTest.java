package com.sewingconnect.domain.model;

import com.sewingconnect.domain.DomainException;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SewingCourseTest {

    @Test
    void createsCourseWithNormalizedText() {
        SewingCourse course = new SewingCourse(
            SewingCourseId.newId(),
            "  Build a lined tote bag  ",
            "  Learn seams, lining, and handles.  ",
            CourseLevel.BEGINNER,
            8,
            Duration.ofHours(3)
        );

        assertThat(course.title()).isEqualTo("Build a lined tote bag");
        assertThat(course.description()).isEqualTo("Learn seams, lining, and handles.");
    }

    @Test
    void rejectsCourseWithoutCapacity() {
        assertThatThrownBy(() -> new SewingCourse(
            SewingCourseId.newId(),
            "Pattern drafting",
            "",
            CourseLevel.INTERMEDIATE,
            0,
            Duration.ofHours(2)
        ))
            .isInstanceOf(DomainException.class)
            .hasMessage("Course capacity must be positive.");
    }

    @Test
    void schedulesSessionForTeacherUsingCourseCapacity() {
        SewingCourse course = new SewingCourse(
            SewingCourseId.newId(),
            "Zip pouch",
            "",
            CourseLevel.ALL_LEVELS,
            6,
            Duration.ofHours(2)
        );
        TeacherId teacherId = TeacherId.newId();
        TimeSlot timeSlot = new TimeSlot(Instant.parse("2026-10-01T09:00:00Z"), Duration.ofHours(2));

        CourseSession session = course.schedule(CourseSessionId.newId(), teacherId, timeSlot);

        assertThat(session.courseId()).isEqualTo(course.id());
        assertThat(session.teacherId()).isEqualTo(teacherId);
        assertThat(session.timeSlot()).isEqualTo(timeSlot);
        assertThat(session.capacity()).isEqualTo(6);
    }
}

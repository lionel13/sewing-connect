# Testing Strategy

The project is prepared for both TDD and BDD.

## TDD

Use focused tests before adding production behavior:

- backend domain rules with JUnit and AssertJ
- backend application services with ports mocked or replaced by fakes
- frontend components with Vitest and Testing Library

## BDD

Use Cucumber scenarios for externally observable behavior and shared vocabulary with stakeholders.

The initial Cucumber scenario only validates that the Spring test context can start. Future scenarios should describe sewing-course-management behavior before implementation.

## Suggested First Business Scenarios

Examples for future work:

- creating a sewing course session
- enrolling a student in a session
- listing upcoming sessions for the teacher
- cancelling or rescheduling a session

These examples are intentionally not implemented in this setup PR.

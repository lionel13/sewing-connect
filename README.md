# Sewing Connect

Sewing Connect is a course-management system for a sewing teacher.

This repository currently contains only the project foundation. No business feature has been implemented yet.

## Stack

- Backend: Spring Boot, Java 21, Maven
- Frontend: React, TypeScript, Vite
- Architecture: hexagonal architecture on the backend
- Testing approach: TDD-ready unit/integration tests and BDD-ready Cucumber scenarios

## Repository Layout

```text
.
├── backend/   # Spring Boot application
├── frontend/  # React application
├── docs/      # Architecture and testing notes
└── .github/   # CI workflow
```

## Backend

```bash
cd backend
mvn test
mvn spring-boot:run
```

The backend package layout reserves the usual hexagonal boundaries:

- `domain`: business concepts and rules
- `application`: use cases and ports
- `adapter`: inbound and outbound adapters
- `config`: framework wiring

## Frontend

```bash
cd frontend
npm install
npm test -- --run
npm run dev
```

## Continuous Integration

The CI workflow runs backend tests and frontend tests/builds on pull requests and pushes to `main`.

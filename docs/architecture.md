# Architecture

Sewing Connect starts with a modular monorepo containing a Spring Boot backend and a React frontend.

## Backend Hexagonal Structure

The backend is organized around hexagonal architecture boundaries:

- `domain`: pure business model and domain services. This package must not depend on Spring or persistence frameworks.
- `application.port.in`: input ports exposed by use cases.
- `application.port.out`: output ports required by use cases.
- `application.service`: application services implementing use cases.
- `adapter.in.web`: HTTP controllers and request/response mapping.
- `adapter.out.persistence`: persistence implementation details.
- `config`: Spring configuration and dependency wiring.

Initial package markers are present only to make these boundaries explicit. Business classes should be added test-first when the first use case is defined.

## Frontend Structure

The frontend keeps a small application shell and separates future code by responsibility:

- `app`: application composition
- `features`: user-facing feature slices
- `shared`: reusable UI, hooks, and utilities

The current React code is intentionally minimal and exists only to verify that the frontend toolchain is healthy.

## Dependency Direction

The intended backend dependency direction is:

```text
adapters -> application -> domain
```

The domain must remain independent from Spring Boot, database drivers, HTTP frameworks, and UI concerns.

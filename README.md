# Lab Inventory API

REST API for managing a laboratory reagent inventory — built with Java 21 and Spring Boot 3.5.

## About

Laboratories track chemical reagents by name, category, quantity, unit and expiration date. This API
provides the backend for that inventory: full CRUD over reagents, input validation, and consistent
error responses.

The domain was chosen deliberately — I have a background in chemistry, so the model reflects how
reagents are actually catalogued rather than an arbitrary example domain.

Storage is currently in-memory; a persistent database is the next planned step (see [Roadmap](#roadmap)).

## Tech stack

- **Java 21**, **Spring Boot 3.5**
- **Maven** (wrapper included — no local Maven installation required)
- **Jakarta Bean Validation** for request validation
- **JUnit 5**, **MockMvc**, **Mockito** for testing
- In-memory repository (generic `Repository<ID, T>` abstraction)

## Getting started

### Prerequisites

- JDK 21 or newer

### Run

```bash
./mvnw spring-boot:run
```

The application starts on `http://localhost:8080`.

By default the inventory starts **empty**. To start with sample reagents, activate the `dev` profile:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### Run the tests

```bash
./mvnw test
```

## Configuration

| Property | Default | Description |
|----------|---------|-------------|
| `lab.seed-data` | `false` | Load sample reagents on startup |

Profiles live in `src/main/resources`:

- `application.yml` — base configuration, safe defaults (no sample data)
- `application-dev.yml` — development profile, enables sample data

Sample data is opt-in by design: a deployment that forgets to set a profile will never inject
fictitious reagents into a real inventory.

## API

### Endpoints

| Method | Path | Description | Success | Errors |
|--------|------|-------------|---------|--------|
| `GET` | `/reagents` | List all reagents | `200` | — |
| `GET` | `/reagents/{id}` | Get one reagent | `200` | `404` |
| `POST` | `/reagents` | Create a reagent | `201` | `400` |
| `PUT` | `/reagents/{id}` | Replace a reagent | `200` | `400`, `404` |
| `DELETE` | `/reagents/{id}` | Delete a reagent | `204` | `404` |

### Reagent fields

| Field | Type | Constraints |
|-------|------|-------------|
| `id` | `Long` | assigned by the server, never sent by the client |
| `name` | `String` | required, not blank |
| `category` | enum | required — `ACID`, `BASE`, `SALT` |
| `amount` | `double` | required, must not be negative |
| `unit` | enum | required — `ML`, `L`, `G`, `KG`, `MG`, `MOL` |
| `expiration` | `LocalDate` | optional, ISO format (`yyyy-MM-dd`) |

### Example — create a reagent

```http
POST /reagents
Content-Type: application/json

{
  "name": "Hydrochloric acid",
  "category": "ACID",
  "amount": 100,
  "unit": "ML",
  "expiration": "2027-06-01"
}
```

```http
201 Created

{
  "id": 1,
  "name": "Hydrochloric acid",
  "category": "ACID",
  "amount": 100.0,
  "unit": "ML",
  "expiration": "2027-06-01"
}
```

### Example — validation failure

```http
POST /reagents
Content-Type: application/json

{ "name": "", "category": "ACID", "amount": -5, "unit": "ML" }
```

```http
400 Bad Request

{
  "name": "name is required",
  "amount": "amount cannot be negative"
}
```

### Example — resource not found

```http
GET /reagents/999
```

```http
404 Not Found

{
  "message": "Reagent with id 999 not found"
}
```

## Architecture

```
cz.jan.labinventoryapi
├── controller    HTTP layer — request mapping, status codes, DTO mapping
├── service       business logic — id generation, existence rules
├── repository    data access — generic in-memory store
├── model         domain model (Reagent, Category, Unit)
├── dto           API contract (ReagentRequest, ReagentResponse)
└── exception     custom exceptions + global handler
```

### Design decisions

**Separate DTOs from the domain model.** `ReagentRequest` (input) has no `id` — the server assigns it,
the client never sends it. `ReagentResponse` (output) does. Keeping these separate from `Reagent` means
the internal model can evolve without breaking the public API contract, and internal fields are never
exposed by accident.

**Constructor injection everywhere.** Dependencies are `final` and visible in the constructor signature,
which keeps components immutable, makes dependencies explicit, and allows instantiating them in tests
without a Spring context.

**Centralised error handling.** A single `@RestControllerAdvice` translates exceptions into HTTP
responses — validation failures into `400` with per-field messages, `ReagentNotFoundException` into
`404`. Controllers contain no error-handling code, and every endpoint returns errors in the same shape.

**Business rules live in the service layer.** Rules such as "a reagent must exist before it can be
updated" belong neither to the HTTP layer nor to storage. Keeping them in the service means they hold
regardless of how the logic is invoked, and they survive replacing the storage implementation.

**Storage behind an interface.** The generic `Repository<ID, T>` abstraction means swapping the
in-memory implementation for a database will not touch the controller or service layers.

## Testing

- `@WebMvcTest` slice tests for the controller — the service is mocked, so the tests verify HTTP
  concerns only: routing, status codes, JSON structure, and delegation to the service.
- Error paths are covered as well: a missing reagent is asserted to produce `404` through the global
  exception handler.

```bash
./mvnw test
```

## Roadmap

- [ ] Persistence with Spring Data JPA and a relational database
- [ ] Filtering and search (by name, category, expiration)
- [ ] Authentication and authorization with JWT
- [ ] OpenAPI / Swagger documentation

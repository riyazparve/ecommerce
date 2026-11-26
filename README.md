# Ecommerce Backend

This repository is a Maven multi-module workspace for backend learning projects. It contains two standalone Spring Boot services and a separate JPA practice application. The root project is an aggregator and dependency-parent POM; it does not start a server itself.

## Architecture

```mermaid
flowchart TD
    Client[HTTP client]
    Parent["ecommerce (Maven parent / reactor)"]
    Catalog["ProductCatalog<br/>Product REST API"]
    Auth["UserAuthService<br/>Signup, login, JWT sessions"]
    Demo["jpa-demo<br/>JPA relationship and CRUD examples"]
    Store["H2 or MySQL<br/>product persistence"]
    FakeStore["FakeStore API<br/>optional product provider"]
    AuthDB["H2 or MySQL<br/>users, roles, sessions"]
    DemoDB["MySQL<br/>JPA demo entities"]

    Parent -. builds .-> Catalog
    Parent -. builds .-> Auth
    Parent -. builds .-> Demo
    Client --> Catalog
    Client --> Auth
    Client --> Demo
    Catalog --> Store
    Catalog --> FakeStore
    Auth --> AuthDB
    Demo --> DemoDB
```

The three applications are sibling modules: each has its own Spring Boot entry point, HTTP controllers, services, and persistence configuration. They are not wired together through an API gateway or service-to-service calls in this repository. A client can call each application's REST API directly. `ProductCatalog` can use either its JPA repositories or the FakeStore client, selected by configuration. `UserAuthService` handles credentials and JWT-backed sessions independently; the other applications do not currently enforce its tokens.

| Module | Responsibility | Main technologies |
| --- | --- | --- |
| [`ProductCatalog`](ProductCatalog/README.md) | Product CRUD, catalog search, paging, and sorting | Spring Web, Spring Data JPA, MySQL/H2, optional FakeStore integration |
| [`UserAuthService`](UserAuthService/README.md) | User signup/login, password hashing, JWT validation, and logout | Spring Web, Spring Data JPA, BCrypt, JJWT, MySQL/H2 |
| [`jpa-demo`](jpa-demo/README.md) | Instagram-style CRUD and entity relationship examples, plus education-domain entities | Spring Web, Spring Data JPA, MySQL, MapStruct |

`ProductCatalog` and `UserAuthService` both use port `8080` by default, so choose a different `server.port` when running them at the same time. `jpa-demo` uses port `9090`.

## Technology and repository layout

- Java 17 and Spring Boot 3.5.16
- Maven wrapper at the repository root (`mvnw.cmd` on Windows)
- Each module has its own `pom.xml`, application source, configuration, and tests.
- Root `pom.xml` aggregates `ProductCatalog`, `jpa-demo`, and `UserAuthService`.

## Build and test

From the repository root in PowerShell:

```powershell
.\mvnw.cmd clean verify
```

To build or test an individual module:

```powershell
.\mvnw.cmd -pl ProductCatalog verify
.\mvnw.cmd -pl UserAuthService verify
.\mvnw.cmd -pl jpa-demo verify
```

## Run an application

Run one module at a time from the repository root:

```powershell
# H2 and FakeStore-backed product service
.\mvnw.cmd -pl ProductCatalog -Dspring-boot.run.profiles=local spring-boot:run

# H2-backed authentication service
.\mvnw.cmd -pl UserAuthService -Dspring-boot.run.profiles=local spring-boot:run

# JPA demo (configure its MySQL database first)
.\mvnw.cmd -pl jpa-demo spring-boot:run

# Payment service
.\mvnw.cmd -f PaymentService\pom.xml spring-boot:run
```

Or, from the `PaymentService` directory:

```powershell
..\mvnw.cmd -f pom.xml spring-boot:run
```

The `dev` profiles for `ProductCatalog` and `UserAuthService` are configured for MySQL; `local` uses an in-memory H2 database. `jpa-demo` currently uses MySQL configuration in `src/main/resources/application.properties`. Set up its database and configure credentials before starting it. See each module README for its APIs and configuration.

### PaymentService API keys

Copy `PaymentService/.env.example` to the ignored `PaymentService/.env`, then put your rotated API keys in that local file:

```powershell
if (-not (Test-Path PaymentService\.env)) {
    Copy-Item PaymentService\.env.example PaymentService\.env
}
```

Use Java properties format: one `NAME=value` per line, without `export` or shell-style quotes. PaymentService reads this file when launched from the repository root or its own directory. When launched from the root, `PaymentService/.env` takes precedence over a root-level `.env`; operating-system environment variables such as `STRIPE_API_KEY` take precedence over either file. Alternatively, set the variable in the same PowerShell window before starting the service:

```powershell
$env:STRIPE_API_KEY = 'your-new-stripe-secret-key'
```

Never commit `.env` or put real credentials in `.env.example` or application properties. Revoke and replace any API key that was previously exposed.

PaymentService handles callbacks at separate endpoints: `/redirectUrl/razorpay` validates Razorpay payment-link callback signatures, and `/redirectUrl/stripe?session_id=...` retrieves the Stripe Checkout Session. Each endpoint acknowledges success only after verifying that payment was paid.

## Authentication signing key

Set `JWT_SECRET` for `UserAuthService` to the Base64 encoding of at least 32 cryptographically random bytes. Keep the same value across restarts and application instances, and provide it through the runtime environment or a secrets manager—not in source control or committed application settings. For example, generate a key in PowerShell:

```powershell
[Convert]::ToBase64String([Security.Cryptography.RandomNumberGenerator]::GetBytes(32))
```

Changing the key invalidates tokens signed with the previous key. The local profile uses an in-memory database, so its user/session data is lost when the process stops.

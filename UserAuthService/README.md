# UserAuthService

`UserAuthService` is a Spring Boot REST application for registering users and managing login sessions using signed JWTs. It is part of the [Ecommerce Backend workspace](../README.md) and runs as an independent application.

## Features

- Register users with a BCrypt-hashed password and a default non-admin role.
- Authenticate active users and return a signed JWT in the `Authorization: Bearer <token>` response header.
- Validate signed, unexpired tokens against the stored active user session.
- Log out by marking the matching session inactive.
- Persist users, roles, and sessions through Spring Data repositories.

## Architecture

`AuthController` exposes the HTTP API and maps request/response DTOs. `AuthService` implements signup, login, token creation and validation, and logout. JPA repositories persist the `User`, `Role`, and `UserSession` models. `AuthConfig` configures BCrypt password encoding and the HMAC signing key; MapStruct maps user entities to API DTOs.

The token includes the user identity and role claims. Validation checks the signature, issuer and authentication type, expiration, active session, and active user status. Logging out invalidates the persisted session, even if the JWT itself has not expired.

## API

Base URL: `http://localhost:8080`

| Method | Path | Description |
| --- | --- | --- |
| `POST` | `/auth/signup` | Register a user; accepts `SignupRequestDto` |
| `POST` | `/auth/login` | Authenticate with `LoginRequestDto`; returns user details and the bearer token header |
| `POST` | `/auth/validateToken` | Validate the bearer token from the `Authorization` header |
| `POST` | `/auth/logout` | Invalidate the bearer token's stored session |

For validation and logout, send `Authorization: Bearer <token>`. Signup and login requests use JSON. Signup returns a `SignupResponseDto`; login returns a `UserDto`.

## Configuration and running

Requires Java 17. From the repository root in PowerShell:

```powershell
.\mvnw.cmd -pl UserAuthService verify
.\mvnw.cmd -pl UserAuthService -Dspring-boot.run.profiles=local spring-boot:run
```

The `local` profile uses an in-memory H2 database. The `dev` profile is configured for MySQL; configure the database and credentials before starting it. The application uses port `8080` unless `server.port` is overridden.

Set `JWT_SECRET` to the Base64 encoding of at least 32 cryptographically random bytes. Keep it stable across restarts and application instances; changing it invalidates previously issued tokens. Supply it through the environment or a secrets manager and never commit signing keys. The local profile's in-memory users and sessions are cleared when the process stops.

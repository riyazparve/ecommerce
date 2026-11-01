# jpa-demo

`jpa-demo` is a Spring Boot application for practicing Spring Data JPA, entity relationships, DTO mapping, validation, and REST CRUD operations. Despite the workspace name, this module is a standalone persistence/API demonstration rather than a runtime dependency of the ecommerce services. See the [workspace architecture](../README.md).

## Domain model and architecture

The main REST API models an Instagram-style domain: users own pages, pages contain posts, and users interact with posts through likes and comments. Controllers expose the APIs, validators check incoming DTOs, MapStruct mappers convert between DTOs and entities, services hold application operations, and Spring Data repositories access the database. The domain entities use UUID identifiers.

The module also contains education-oriented JPA models such as learners, instructors, classes, batches, and teacher ratings. These are persistence examples and are not exposed through the Instagram REST controllers.

## API

The application listens on port `9090` by default.

| Resource | Base path | Capabilities |
| --- | --- | --- |
| Users | `/users` | List, retrieve, search by username/email, create, update, and delete |
| Pages | `/pages` | List, retrieve, list by creator, create, update, and delete |
| Posts | `/posts` | List, retrieve, list by page, create, update, and delete |
| Comments | `/comments` | List, retrieve, list by post/user, create, update, and delete |
| Likes | `/likes` | List, retrieve, list by post/user, create, update, and delete |

Create and update operations accept the corresponding JSON DTO and validate it before saving. Entity IDs in paths are UUIDs.

## Configuration and running

Requires Java 17 and a MySQL instance. `src/main/resources/application.properties` configures a database named `jpa_demo` on `localhost:3306`; create that database and configure valid credentials before starting the application. Hibernate schema updates and SQL logging are enabled by the current application configuration.

From the repository root in PowerShell:

```powershell
.\mvnw.cmd -pl jpa-demo verify
.\mvnw.cmd -pl jpa-demo spring-boot:run
```

Do not commit database credentials.

# ProductCatalog

`ProductCatalog` is a Spring Boot REST application for managing and searching products. It is part of the [Ecommerce Backend workspace](../README.md), but can be built and run independently.

## Features

- Create, retrieve, replace, partially update, and delete products.
- Create multiple products in one request.
- Browse products with pagination and sorting, and search products by name.
- Map API DTOs to domain entities and translate persistence or FakeStore results.
- Select between a JPA-backed product store and the FakeStore API with configuration.

## Architecture

HTTP requests are handled by `ProductController` and `SearchController`. Controllers validate request data and use DTO mappers, then delegate to product or search services. The product service is selected from `product.service.selected-service`:

- `storageProductService` uses Spring Data repositories for `Product` and `Category`.
- `fakeStoreProductService` calls the FakeStore API and maps its DTOs to the local product model.

The application also contains centralized controller advice, product validators, and product/category entities. Search uses the JPA product repository and returns paged DTO results.

## API

Base URL: `http://localhost:8080`

| Method | Path | Description |
| --- | --- | --- |
| `GET` | `/products` | List products |
| `GET` | `/products/{productId}` | Get a product by ID |
| `POST` | `/products` | Create a product |
| `POST` | `/products/all` | Create a list of products |
| `PUT` | `/products/{productId}` | Replace a product |
| `PATCH` | `/products/{productId}` | Update a product |
| `DELETE` | `/products/{productId}` | Delete a product |
| `GET` | `/search/all?page=0&size=5&sortBy=id&ascending=true` | Browse products with paging and sorting |
| `GET` | `/search` | Search products by name with a `SearchRequestDto` JSON body |

The search request body has `searchTerm`, `pageNumber`, and `pageSize` fields. Search results are ordered by descending product ID and price. Product request and response bodies use `ProductDto`.

## Configuration and running

Requires Java 17. From the repository root in PowerShell:

```powershell
.\mvnw.cmd -pl ProductCatalog verify
.\mvnw.cmd -pl ProductCatalog -Dspring-boot.run.profiles=local spring-boot:run
```

The `local` profile configures an in-memory H2 database and selects `fakeStoreProductService`. The `dev` profile configures MySQL and selects `storageProductService`; set up the database and provide valid credentials before using it. To select a provider explicitly, set `product.service.selected-service` to `fakeStoreProductService` or `storageProductService` in the active configuration.

The application uses port `8080` unless `server.port` is overridden. `FetchModes.MD` contains notes on JPA fetching behavior explored by this module.

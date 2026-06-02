# SpringAiIntegration

Integration project for Spring AI.

## Local Development Environment

This project uses Docker Compose to provide the necessary infrastructure for local development.

### Prerequisites

- Docker
- Docker Compose

### Getting Started

To start the infrastructure (PostgreSQL and Redis), run:

```bash
docker compose up -d
```

*Note: The project uses a `.env` file for environment variables. You can customize ports and credentials there.*

To stop the services:

```bash
docker compose down
```

To view logs:

```bash
docker compose logs -f
```

### Spring Boot Integration

This project uses `spring-boot-docker-compose`. When you run the application via Maven or your IDE, Spring Boot will automatically detect the `docker-compose.yml` file and start the services if they are not already running.

### Services

- **PostgreSQL (pgvector)**: Accessible at `localhost:5432`
  - Database: `knowledgeassist`
  - User: `postgres`
  - Password: `postgres`
- **Redis**: Accessible at `localhost:6379`

### Persistence

Data is persisted using Docker volumes. PostgreSQL data is stored in the `postgres-data` volume.

## Testing

This project uses JUnit 5, Mockito, and Testcontainers for testing.

### Running Tests

To run all tests and generate a coverage report:

```bash
mvn test
```

### Coverage Reports

After running the tests, the JaCoCo coverage report can be found at:
`target/site/jacoco/index.html`

The project has a target code coverage of **95%**.

### Integration Tests

Integration tests use **Testcontainers** to spin up real instances of PostgreSQL and Redis. Ensure Docker is running when executing integration tests.


# Student Management System

Система управління студентами на Spring Boot

## Технології

- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- Spring Web
- Spring Validation
- Lombok
- Swagger/OpenAPI
- JUnit 5
- Mockito

## Функціональність

- CRUD операції для студентів
- Валідація даних
- REST API
- Swagger документація
- Тестування

## Тестування

Проєкт включає тести для:

- Сервісів (`StudentServiceImplTest`)
- REST контролерів (`StudentRestControllerTest`)

### Запуск тестів

```bash
mvn test
```

### Тестовий покриття

- Unit тести для сервісів з використанням Mockito
- Інтеграційні тести для REST контролерів з використанням MockMvc
- Перевірка всіх CRUD операцій
- Валідація відповідей API

## API Документація

Swagger UI доступний за адресою: `http://localhost:8080/swagger-ui.html`

## Запуск проєкту

1. Клонуйте репозиторій
2. Запустіть команду:

```bash
mvn spring-boot:run
```

## Ліцензія

MIT

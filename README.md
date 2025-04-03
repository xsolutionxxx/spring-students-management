# Spring Boot Student Management API

REST API для управління студентами, побудований на Spring Boot.

## Функціональність

- CRUD операції для студентів
- Валідація даних
- Логування операцій
- Обробка помилок
- Swagger документація
- H2 база даних (in-memory)

## Технології

- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- H2 Database
- Lombok
- Swagger/OpenAPI
- SLF4J + Logback

## Вимоги

- JDK 17 або вище
- Maven 3.6 або вище

## Встановлення

1. Клонуйте репозиторій:

```bash
git clone https://github.com/yourusername/spring-students-management.git
```

2. Перейдіть до директорії проекту:

```bash
cd spring-students-management
```

3. Зберіть проект:

```bash
mvn clean install
```

4. Запустіть додаток:

```bash
mvn spring:boot run
```

## API Endpoints

- GET /api/students - Отримати всіх студентів
- GET /api/students/{id} - Отримати студента за ID
- POST /api/students - Створити нового студента
- PUT /api/students/{id} - Оновити студента
- DELETE /api/students/{id} - Видалити студента

## Логування

Додаток використовує SLF4J з Logback для логування. Логи зберігаються в:

- Консоль
- Файл: logs/student-management.log

Рівні логування:

- DEBUG: детальна інформація про операції
- INFO: успішні операції
- ERROR: помилки та винятки

## Обробка помилок

Додаток обробляє наступні типи помилок:

- 404 NOT FOUND: студент не знайдений
- 400 BAD REQUEST: помилки валідації
- 500 INTERNAL SERVER ERROR: неочікувані помилки

## Swagger UI

Swagger документація доступна за адресою:

```
http://localhost:8080/swagger-ui.html
```

## H2 Console

Консоль H2 бази даних доступна за адресою:

```
http://localhost:8080/h2-console
```

## Тестування

Для запуску тестів виконайте:

```bash
mvn test
```

## Ліцензія

MIT

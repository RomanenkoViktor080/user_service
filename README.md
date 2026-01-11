# User service

Сервис для сокращения ссылок

Стек:

- Язык / платформа: `Java` + `Spring Boot 3.5.4`
- БД: `PostgreSQL`, `hibernate`, `JPA` + `JDBC`, миграции - `Liquibase`;
- Асинхронность / интеграции: `Apache Kafka`, `Avro`, `schema registry`
- Кеширование: `Redis`
- Утилиты: `MapStruct`, `Lombok`
- API: `REST`, `OpenAPI/Swagger`

# Подробнее:

* [AuthController.java](src/main/java/school/faang/user_service/controller/user/AuthController.java) - spring security,
  енпоинты для авторизации регистрации пользователя. security конфиги и Spring Security Filter Chain
  `src/main/java/school/faang/user_service/config/security`.
    - `src/main/java/school/faang/user_service/service/auth` - сервисы для обработки jwt токена и аутентификации
* [ApiExceptionHandler.java](src/main/java/school/faang/user_service/controller/ApiExceptionHandler.java) - глобальная
  обработка ошибок
* [BaseFilterBuilder.java](src/main/java/school/faang/user_service/entity/filter/BaseFilterBuilder.java) - абстрактный
  билдер фильтров. Пример конкретной реализации - `src/main/java/school/faang/user_service/filters/goal/*`.
  Используется для быстрого создания фильтров сущности, с возможностью расширения, удобством использования.
* `src/main/java/school/faang/user_service/entity` - JPA сущности
* `src/main/java/school/faang/user_service/dto` - REST dto
* `src/main/java/school/faang/user_service/controller` - Контроллеры
* `src/main/resources/avro` - avro схемы
* `src/test/java/school/faang/user_service` - тесты
* `src/main/java/school/faang/user_service/repository` - JPA репозитории
* `src/main/java/school/faang/user_service/service` - Сервисы


---

# Renting Application

![Spring Boot](https://img.shields.io/badge/SpringBoot-2.7-brightgreen)
![Java](https://img.shields.io/badge/Java-17-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![Docker](https://img.shields.io/badge/Docker-24.0-blue)

## 📌 О проекте

Приложение для управления арендой объектов. Пользователи могут создавать, просматривать и бронировать объекты, оставлять рейтинги, а администраторы управлять пользователями и объектами.

Особенности проекта:

* Аутентификация и авторизация через JWT.
* REST API с использованием Spring Boot.
* База данных PostgreSQL.
* Docker и Docker Compose для упрощённого развертывания.
* Swagger документация для API.

---

## 🛠 Технологии

* Java 17
* Spring Boot
* Spring Security + JWT
* PostgreSQL
* Maven
* Docker / Docker Compose
* Swagger UI

---

## ⚡ Установка и запуск

### Локально

1. Клонируйте репозиторий:

```bash
git clone <repo-url>
cd spring-project1
```

2. Запустите приложение через Maven:

```bash
mvn spring-boot:run
```

3. Swagger UI для тестирования API доступен по адресу:

```
http://localhost:8080/swagger-ui/index.html
```

---

### Через Docker Compose

1. Соберите и запустите контейнеры:

```bash
docker compose up --build
```

2. Приложение и база PostgreSQL запустятся автоматически.

---

### DockerHub (вариант с образом)

1. Собрать Docker образ:

```bash
docker build -t mydockerhubusername/renting-app .
```

2. Войти в DockerHub и залить образ:

```bash
docker login
docker push mydockerhubusername/renting-app:latest
```

3. После этого можно запускать через `docker run` или через `docker-compose.yml`.

---

## 📄 API

* Swagger UI: `http://localhost:8080/swagger-ui/index.html#/`
* Raw JSON документация: `http://localhost:8080/v3/api-docs`

---

## ⚙️ Структура проекта

```
src/
 ├─ main/
 │   ├─ java/com/example/renting/
 │   │   ├─ controller/
 │   │   ├─ dto/
 │   │   ├─ model/
 │   │   ├─ repo/
 │   │   ├─ service/
 │   │   └─ security/
 │   └─ resources/
 └─ test/
```

---

## 🚀 TODO / Планы

* Разобраться с 401 ошибками авторизации.
* Протестировать все эндпоинты через Postman.
* Подумать над интеграцией с фронтендом.

---

## 📚 Дополнительно

В `HELP.md` и `text.txt` содержатся советы по деплою и работе с Docker

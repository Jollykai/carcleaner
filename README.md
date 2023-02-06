# Read Me
This is a test task - Springboot RestAPI App for Car Wash service.

# Objective
Write a program for a car wash.

* There is a list of services that customers can choose.
* Customers can sign up for a car wash and see how long they have to wait for their time.
  Pre-registration for free time is possible.
* Administrators can add new and remove old services from the system.
---
* To store data, use PostgreSQL
* Implement Rest Api
* Use Swagger for the description
* To deploy the database, use Flyway

### Used stack:
* Java
* Springboot
* Maven
* JPA
* PostgreSQL
* Flyway
* Swagger

# Getting Started

1. Run DB in Docker:

        docker run --rm -p 5432:5432 -e POSTGRES_PASSWORD=0000 postgres

2. Run App

# How to test
* As app using Swagger it's possible to use it's UI for tests
  http://localhost:8080/swagger-ui.html
* Also program was tested by Postman

### Note
Some methods available only for manager and require authentication

Navigate to http://localhost:8080/login to login
* username: manager
* password: manager

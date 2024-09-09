# Spring Boot CRUD Operations on Device Entity

## Table of Contents
- [About](#about)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Running the Application](#running-the-application)
- [Running with Docker](#running-with-docker)
- [Unit Testing](#unit-testing)
- [API Endpoints](#api-endpoints)
- [Testing the API with Postman](#testing-the-api-with-postman)


---

## About

This project demonstrates how to implement CRUD (Create, Read, Update, Delete) operations on a Device entity using Spring Boot. The application uses an in-memory H2 database for data persistence and integrates with Spring Data JPA for easy interaction with the database. You can test the RESTful APIs using Postman.

### Features:
- CRUD operations on a Device entity.
- Lombok is used to reduce boilerplate code for the model classes.
- H2 in-memory database for lightweight data management.
- Postman integration for testing RESTful APIs.
- Docker support for containerized deployment.
- Unit tests using JUnit and Mockito for service-layer testing.

---

## Technologies
- *Spring Boot*
- *Lombok*
- *H2 Database*
- *Spring Data JPA*
- *Spring Web*
- *Docker*
- *JUnit*
- *Mockito*
- *Postman (for testing)*

---

## Prerequisites
Before running this project, ensure you have the following installed:
- *Java 17*
- *Maven* (for managing dependencies)
- *Docker* (for running the containerized application)
- *Postman* (or any REST client for API testing)

---

## Setup

1. *Clone the repository:*


`git clone https://github.com/Takwa-eng/Device-Management.git`

2. *Navigate to the project directory:*


`cd your-repo-name`

3. *Build the project using Maven:*

`mvn clean install`

4. *Run the application:*

`mvn spring-boot:run`

The application will start on http://localhost:9090

---

## Running the Application

The application uses an in-memory H2 database, so no setup is required for the database. You can view the H2 database console at:

- *H2 Console URL:* http://localhost:9090/h2-console
- *JDBC URL:* jdbc:h2:mem:testdb
- *Username:* sa
- *Password:*
  (leave blank)

---

## Running with Docker

You can run this project using Docker by following these steps:

1. *pull the Docker image:*

`docker pull takwa854/device-managment-images`

2. *Run the Docker container:*

`docker run -p 9090:9090 takwa854/device-managment-images`

The application will now be running in a Docker container, accessible at http://localhost:9090

---

## Unit Testing

This project includes unit tests to validate the functionality of the DeviceService layer. We use JUnit  and Mockito to mock dependencies and write isolated tests.

### Running Unit Tests

To run the unit tests, execute the following Maven command:

`mvn test`

This will run all unit tests and provide a report on the test results.

---

## API Endpoints

Here are the available REST API endpoints:

| HTTP Method | Endpoint            | Description                     | Request Body |
|-------------|---------------------|---------------------------------|--------------|
| POST        | /api/devices        | Add a new device                | JSON         |
| GET         | /api/devices/{id}   | Get a device by ID              | N/A          |
| GET         | /api/devices        | Get a list of all devices       | N/A          |
| DELETE      | /api/devices/{id}   | Delete a device by ID           | N/A          |
| PUT         | /api/devices/{id}   | Update a device                 | JSON         |
| GET         | /api/devices        | Get devices by brand            | Query Param  |


## Testing the API with Postman

Use Postman or any other API client to interact with the API. Below are the available API endpoints and sample requests, as mentioned above.
> *N.B:*
**collection postman**
> [a relative link](Device-Management/collectionPostman)
`Device-Management/collectionPostman`




﻿# Rock ✊ Paper 🤚 Scissors ✌ Game

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup and Installation](#setup-and-installation)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [Code Quality and Coverage](#code-quality-and-coverage)
- [API Documentation](#api-documentation)
- [Continuous Integration](#continuous-integration)
- [Docker](#docker)
- [Database](#database)
- [Design Principles](#design-principles)

## Introduction
This project is a simple implementation of the classic Rock-Paper-Scissors game. It allows two players to play the game, keeps track of scores, and supports rounds. Built using Java 17 and Spring Boot, the game follows SOLID principles for software design. Additionally, it includes email functionality to notify players about game invitations.

## Features
- Create and Join game session
- Email notification for game invitation
- Make move and determine winner
- Retrieve game details and status
- RESTful API
- In-memory H2 database
- Swagger API documentation
- Continuous integration with GitHub Actions
- Code coverage with JaCoCo
- Docker support

## Technologies Used
- **Java 17**: Programming language
- **Spring Boot**: Framework for building the application.
- **Gradle**: Build automation tool.
- **H2 Database**: In-memory database.
- **Swagger**: RESTful Documentation.
- **JaCoCo**: Code coverage tool.
- **GitHub Actions**: Continuous integration.
- **Docker**: Containerization platform.
- **Git**: Version control system.

## Setup and Installation
1. **Clone the repository:**
   ```sh
   git clone https://github.com/snehalmore94/rockpaperscissors-api.git
   cd rock-paper-scissors
2. **Build the project:**
   ```sh
    ./gradlew build
3. **Run the application:**
    ```sh
    ./gradlew bootRun

## Running the Application 
You can access the Swagger UI for API documentation at http://localhost:8080/swagger-ui.html.

## Testing
1. **Run the test cases using the following command:**
    ```sh
    ./gradlew test

## Code Quality and Coverage
Code coverage is measured using JaCoCo. The coverage report is generated in build/reports/jacoco/test/jacocoTestReport.csv.

## API Documentation

The Rock-Paper-Scissors game provides a RESTful API for interacting with the game. Below are the available endpoints and their purposes.

### Endpoints

#### 1. Start a New Game
- **Endpoint:** `/api/games`
- **Method:** `POST`
- **Purpose:** Player 1 starts a new game session and sends an email request to Player 2. You need to provide email id of player 2 in request. The email contains the game ID to join the game.

#### 2. Join the existing game started by player 1.
- **Endpoint:** `/api/games/{id}/join`
- **Method:** `PUT`
- **Purpose:** Player 2 joins an existing game by providing the game ID and their name. 

#### 3. Make a Move
- **Endpoint:** `/api/games/{id}/move`
- **Method:** `PUT`
- **Purpose:** A player makes a move in the game by providing the game ID, player name and their move (ROCK, PAPER, or SCISSORS).

#### 4. Get Game Details
- **Endpoint:** `/api/games/{id}/result`
- **Method:** `GET`
- **Purpose:** Retrieves the details of the game by providing the game ID, including the current status and moves made.

### Swagger UI
You can also explore and test the API using the Swagger UI available at `http://localhost:8080/swagger-ui.html`.
## Continuous Integration
GitHub Actions is used for continuous integration. The workflow is defined in .github/workflows/ci.yml.

## Docker
The Docker image for this application is automatically built and pushed to Docker Hub using GitHub Actions.
## Database
The application uses an in-memory H2 database for development and testing.

## Design Principles
SOLID (single responsibility, open-closed, Liskov subsitution, interface segragation, dependency inversion) principle

## Conclusion
This project demonstrates a simple yet comprehensive implementation of a Rock-Paper-Scissors game using modern Java and Spring Boot practices. It includes features like multiple rounds, score tracking, and RESTful APIs, and it leverages tools like JaCoCo for code coverage, GitHub Actions for CI/CD, and Docker for containerization.

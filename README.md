# VET API SECURITY

This is the home page of our project called "API SECURITY".

## Goal

The final objective is to have a double authentication to access
to a specific page. In our case, it will be a page which contains
every log of personal connection.

## Installation & Execution

### Need

- Spring Boot IDE : https://spring.io/tools
- Java
- Maven

### Project

- Project -> Maven
- Language -> Java 
- Spring Boot 2.2.4
- Packaging -> Jar
- Java 11
- Dependencies -> 

### Run

- Command line
```sh
$ mvn spring-boot:run
```

- Spring Boot IDE -> Run as Java Application

## Technologies

- For this project we are going to use different technology. The
main technology will be Spring Boot which is a framework for Java, but
it can also be use for Java EE which is our case.

- Spring Boot will use modules to easily create application. With
		the help of Maven, you just need to specify the package you need and
		Spring build the entire project.

- For the two authentication, we are going to use Google
		Authenticator and Twilo.

### Google Authenticator

We will use the API of Google to authorize access to user or the
		creation of account. Once the user is connect with his Google account,
		he has access to the web site except for the page of logs.

### Twilo

To access the log web site, the user will receive a code on his
		smartphone and he will have to enter this code on the website to
		access the log page.
		
## People

We are a group of four students to create this project
- Natacha RENOU
- Sébastien LERAY
- Thomas FURET
- Julien ROYON CHALENDARD

## Companies

- Orange
- Université Rennes 1
- Istic


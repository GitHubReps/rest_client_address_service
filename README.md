REST API Service (test task for job application)

Key goals:

Develop a REST service project using Spring Boot, Spring Web, Spring Data JPA, H2 Database

The service should provide operations with 2 related entities: Client and Address.
Connection requirement: OneToMany (One client has many addresses)

Objects mush have id and name as fields.

Implement operations:

1) View the list of all Clients (without associated address)
2) View the list of all Clients (without associated address), filtered by a substring included in name
3) Add a new Client;
4) Remove existing client by id;
5) Get list of all addresses for given Client id;
6) Add a new address for given Client id;
7) Remove Address by Id;

Solution:

1) Project structure:
- Config: 
AppConfig file provides support for ModelMapper class, useful for form mapping;
SwaggerConfig file configures swagger configuration
WebSecurityConfig file is required for basic security settings

-Controller:
ClientController file is the main REST Controller file with all the necessary methods

-Dto:
This package is required to transfer data (such as forms usage)

-Exceptions:
Package represents 3 common exceptions:
Client with name already created
No entity is found
And no value present

-Model:
Package for Client and Address entities

-Repository:
Provides JPA interfaces

-Service:
Provides service layer for data

-Util:
GlobalExceptionHanlder for exception handling

DemoApplication - main application method

resources:
Flyway migration with 6 injected values

test:
JUnit4 tests

2) Status:
Project if fully developed, ready for further usage

3) Main issues:
- It is unusual to have OneToMany connection between a client and an address, ManyToMany should be better;
- Or it is also possible to create third embeddable database;
- Each address is connected with the client, deleting address just by id could be harmful;
- It is not safe to work with ID fields, auto-generated UUID is preferred


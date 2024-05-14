# Library-Management

# Project Description:
Build a Library Management System API using Spring Boot. The system should allow librarians to manage books, patrons, and borrowing records.





#Feature 
This is simple follow for challenge according to pdf

# Technologies Used


| Project Kazyon DashBoard | Version |
|--------------------------|---------|
| Spring Boot              | 3.2.3   |
| Java                     | 17      |        |
| Spring Security          | 6       |
| Datbase                  | H2      | 

We also added a sql script so each time it will load some user data like buyer and seller and some product into database
keep in mind that all user have the same password which is```pass123 ```

## API Endpoints

### Book Management Endpoints:

- **GET** `/api/books`: Retrieve a list of all books.
- **GET** `/api/books/{id}`: Retrieve details of a specific book by ID.
- **POST** `/api/books`: Add a new book to the library.
- **PUT** `/api/books/{id}`: Update an existing book's information.
- **DELETE** `/api/books/{id}`: Remove a book from the library.

### Patron Management Endpoints:

- **GET** `/api/patrons`: Retrieve a list of all patrons.
- **GET** `/api/patrons/{id}`: Retrieve details of a specific patron by ID.
- **POST** `/api/patrons`: Add a new patron to the system.
- **PUT** `/api/patrons/{id}`: Update an existing patron's information.
- **DELETE** `/api/patrons/{id}`: Remove a patron from the system.

### Borrowing Endpoints:

- **POST** `/api/borrow/{bookId}/patron/{patronId}`: Allow a patron to borrow a book.
- **PUT** `/api/return/{bookId}/patron/{patronId}`: Record the return of a borrowed book by a patron.





# Installation
Ensure Java is installed on your machine.
Clone the repository:

Copy code
```git clone https://github.com/yourusername/library-management.git```

use this command if you have mvn install

```mvn spring-boot:run```

# Feature
App with build with jwt token and role based security 

# Endpoint 
this is a simple postman collaction with some of the endpoint
``` https://documenter.getpostman.com/view/31760171/2sA2xb6axP```

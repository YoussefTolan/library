# library
________________________________________
Library Management System API Documentation
Table of Contents
1.	Introduction
2.	Prerequisites
3.	Getting Started
o	Clone the Repository
o	Build the Project
o	Run the Application
4.	API Documentation
5.	Interacting with the API
o	Available Endpoints
o	Request and Response Formats
6.	Error Handling
7.	Testing
8.	Common Issues and Troubleshooting
9.	Conclusion
1. Introduction 
This documentation provides an overview of the Library Management System API, developed using Spring Boot. The API allows users to manage books, patrons, and borrowing records within a library system. This document covers how to set up, run, and interact with the API.
2. Prerequisites 
Before running the application, ensure you have the following installed:
•	Java 11 or higher
•	Maven 3.6.0 or higher
•	Git (optional, for cloning the repository)
•	MySQL (or any other configured database)


3. Getting Started 
Clone the Repository : git clone https://github.com/youssefTolan /library-management-system.git
cd library-management-system
To get started, clone the repository from your version control system (e.g., GitHub):
Build the Project : mvn clean install
Run the Application : mvn spring-boot:run
4. API Documentation 
Swagger UI
http://localhost:8080/swagger-ui.html
5. Interacting with the API 
Available Endpoints 
Below is a list of available API endpoints grouped by resource:
•	Books
o	POST /books - Create a new book
o	PUT /books/{id} - Update a book by ID
o	GET /books - Get all books
o	GET /books/{id} - Get a book by ID
o	DELETE /books/{id} - Delete a book by ID
•	Patrons
o	POST /patrons - Create a new patron
o	PUT /patrons/{id} - Update a patron by ID
o	GET /patrons - Get all patrons
o	GET /patrons/{id} - Get a patron by ID
o	DELETE /patrons/{id} - Delete a patron by ID
•	Borrowing Records
o	POST /borrow/{bookId}/patron/{patronId} - Borrow a book
o	PUT /return/{bookId}/patron/{patronId} - Return a borrowed book
Request and Response Formats 
All endpoints accept and return JSON data. Below are example request and response formats for key operations:
Create a Book:
Request: {
    "title": "Clean Code",
    "author": "Robert C. Martin",
    "isbn": "9780132350884",
    "publicationYear": 2008
}
Response: {
    "id": 1,
    "title": "Clean Code",
    "author": "Robert C. Martin",
    "isbn": "9780132350884",
    "publicationYear": 2008
}

Borrow a Book:
Request: No request body needed.
Response: {
    "id": 1,
    "book": {
        "id": 1,
        "title": "Clean Code",
        "author": "Robert C. Martin"
    },
    "patron": {
        "id": 1,
        "name": "John Doe"
    },
    "borrowDate": "2024-08-01T10:15:30",
    "returnDate": null
} 

6.Error Handling 
The API uses custom exception handling to return meaningful error messages. Common error responses include:
•	400 Bad Request: Returned for validation errors.
•	404 Not Found: Returned when a requested resource (e.g., book or patron) does not exist.
•	409 Conflict: Returned for data integrity violations (e.g., duplicate entries).
Example Error Response
{
    "timestamp": "2024-08-01T10:15:30",
    "status": 404,
    "error": "Not Found",
    "message": "NO_MATCHING_BOOK_RECORD_FOR_THIS_ID",
    "path": "/books/999"
}
7.Testing 
Unit tests are provided for the service and controller layers. To run the tests, use the following command:
mvn test
The tests are located in the src/test/java directory and cover various scenarios to ensure the API behaves as expected

8.Common Issues and 
Common Issues
•	Application Fails to Start: Ensure your database connection settings in application.properties are correct.
•	Swagger UI Not Loading: Ensure Swagger dependencies are correctly configured and the application is running on port 8080.
Troubleshooting
•	Check logs for detailed error messages.
•	Ensure all required dependencies are included in the pom.xml.
•	Verify database connections and migrations are applied correctly.


10. Conclusion 
This documentation provides a comprehensive guide to running and interacting with the Library Management System API. For any further questions or contributions, feel free to reach out or open an issue on the project's repository.





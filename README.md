## Don't Wreck My House – Java Reservation Management System
An end-to-end **Java console application** for managing vacation home reservations. Designed with clean architecture principles and built using object-oriented programming, layered design, and modern Java tools.

This project simulates the operations of a platform similar to Airbnb, with support for adding, viewing, editing, and canceling reservations between guests and hosts.

### Features
* **View Reservations:**
  * Search and list all reservations for a specific host.
* **Add Reservation:**
  * Schedule a new reservation for a guest and host with built-in validation for dates and availability.
* **Edit Reservation:**
  * Modify reservation details with real-time error handling and feedback.
* **Cancel Reservation:**
  * Remove upcoming reservations while preserving the integrity of historical data.
* **User Input Validation:**
  * Graceful handling of invalid dates, email addresses, and past/future logic.
* **Domain-Specific Rules:**
  * Prevent overlapping reservations
  * Restrict reservation cancellation after the start date
  * Accurate calculation of costs using `BigDecimal`

### Tech Stack
* **Language:** Java
* **Build Tool:** Maven
* **Architecture:** MVC pattern with service and repository layers
* **Testing:** JUnit for domain and data layer testing
* **Key Java Features:** Exception handling, BigDecimal, LocalDate, Spring dependency injection

### Example Interactions
* Search for a host by email and display their reservations
* Attempt to schedule a reservation with invalid dates (e.g., yesterday)
* Cancel a reservation, with safeguards against deleting past entries
* See a full cost breakdown during reservation confirmation

### How to Run
1. Clone the repository:
```declarative
git clone https://github.com/nathann07/Dont-Wreck-My-House.git
```
2. Open the project in an IDE that supports Maven (e.g., IntelliJ IDEA, Eclipse).
3. Locate the App class in src/main/java/learn/mastery and run it as a Java application.
4. Follow the on-screen prompts to interact with the app.
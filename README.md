# Online-banking-system-springboot
A Spring Boot-based banking application for managing user authentication, financial transactions, and user profiles. Secured with JWT and integrated with email notifications.

## Features

- **User Authentication**:  
  - Register new accounts with email confirmation.  
  - Login using JWT for secure API access.  

- **Financial Transactions**:  
  - Deposit, withdraw, and transfer funds.  
  - View transaction history with date filtering.  

- **User Management**:  
  - Retrieve all users.  
  - Check account balance and profile details.  

- **Email Integration**:  
  - Automated email notifications for account creation.  

## Technologies Used

- **Backend**: Spring Boot, Spring Security, JWT.  
- **Database**: Spring Data JPA (configured for MySQL).  
- **Utilities**: Lombok, MapStruct (for mappers).  
- **Email**: Java Mail Sender.  

## Setup Instructions

### Prerequisites
- Java 17+  
- Maven  
- MySQL/PostgreSQL  
- SMTP email service (e.g., Gmail)  

### Steps
1. **Clone the repository**:  
   ```bash
   git clone [repository-url]
   
### Configure the database:  
Update `application.properties` with your database credentials:  
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bank_db
spring.datasource.username=root
spring.datasource.password=root
```
### Configure email settings:
Add SMTP details to application.properties:
```properties
spring.mail.username=your-email@gmail.com  
spring.mail.password=your-app-password
```
### Build and run:
```bash
mvn spring-boot:run

```

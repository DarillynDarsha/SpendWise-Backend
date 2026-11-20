\# SpendWise – Backend



A RESTful backend application for \*\*SpendWise\*\*, a personal finance management system. It provides secure user authentication, transaction management, and profile management using Spring Boot, Spring Security, JWT, and MySQL.



\## 🚀 Features



\- \*\*User Registration:\*\* Register new users with name, email, phone number, and password.

\- \*\*User Authentication:\*\* Secure login using email and password.

\- \*\*JWT Authentication:\*\* Token-based authentication for protected API endpoints.

\- \*\*Password Encryption:\*\* Passwords are encrypted using BCrypt.

\- \*\*Transaction Management:\*\* Create, view, update, and delete transactions.

\- \*\*User Profile Management:\*\* Retrieve and update the logged-in user's profile.

\- \*\*User-specific Data:\*\* Transactions are associated with the authenticated user.

\- \*\*Input Validation:\*\* Validation for registration fields.



\## 🛠️ Tech Stack



| Technology | Purpose |

|---|---|

| Java 21 | Programming Language |

| Spring Boot 4.1.2 | Backend Framework |

| Spring Web MVC | REST API Development |

| Spring Data JPA | Database Operations |

| Spring Security | Application Security |

| JWT (JJWT 0.12.6) | Token-based Authentication |

| MySQL | Relational Database |

| Hibernate | ORM |

| Maven | Dependency Management |

| BCrypt | Password Encryption |



\## 📁 Project Structure



```text

spendwise-backend/

│

├── src/

│   ├── main/

│   │   ├── java/

│   │   │   └── com/spendwise/

│   │   │       ├── controller/

│   │   │       │   ├── AuthController.java

│   │   │       │   ├── TransactionController.java

│   │   │       │   └── UserController.java

│   │   │       │

│   │   │       ├── entity/

│   │   │       │   ├── User.java

│   │   │       │   └── Transaction.java

│   │   │       │

│   │   │       ├── repository/

│   │   │       │   ├── UserRepository.java

│   │   │       │   └── TransactionRepository.java

│   │   │       │

│   │   │       ├── security/

│   │   │       │   ├── JwtAuthenticationFilter.java

│   │   │       │   └── SecurityConfig.java

│   │   │       │

│   │   │       ├── service/

│   │   │       │   ├── CustomUserDetailsService.java

│   │   │       │   ├── JwtService.java

│   │   │       │   └── TransactionService.java

│   │   │       │

│   │   │       └── SpendwiseBackendApplication.java

│   │   │

│   │   └── resources/

│   │       ├── application.example.properties

│   │       └── application.properties

│   │

│   └── test/

│

├── .gitignore

├── pom.xml

├── mvnw

└── mvnw.cmd

```



\## ⚙️ Prerequisites



Make sure the following are installed:



\- Java Development Kit (JDK) 21

\- MySQL Server

\- MySQL Workbench (optional)

\- Git



\## 💻 Installation and Setup



\### 1. Clone the Repository



```bash

git clone https://github.com/DarillynDarsha/SpendWise-Backend.git

```



\### 2. Navigate to the Project Directory



```bash

cd SpendWise-Backend

```



\### 3. Create the MySQL Database



Open MySQL Workbench or your MySQL terminal and execute:



```sql

CREATE DATABASE spendwise;

```



\### 4. Configure Database and JWT Settings



Navigate to:



```text

src/main/resources/

```



Create a file named:



```text

application.properties

```



Use the `application.example.properties` file as a reference and configure your local database credentials.



Example:



```properties

spring.application.name=spendwise-backend



spring.datasource.url=jdbc:mysql://localhost:3306/spendwise

spring.datasource.username=YOUR\_MYSQL\_USERNAME

spring.datasource.password=YOUR\_MYSQL\_PASSWORD



spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver



spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true



app.jwt.secret=YOUR\_BASE64\_ENCODED\_256\_BIT\_SECRET

```



\*\*Important:\*\* Replace the placeholder values with your own credentials and a securely generated JWT secret. Never commit your actual `application.properties` file or expose database passwords and JWT secrets.



\### 5. Run the Application



On Windows, execute:



```powershell

.\\mvnw.cmd spring-boot:run

```



The backend application will start at:



```text

http://localhost:8080

```



\## 🔗 REST API Endpoints



Base URL:



```text

http://localhost:8080

```



\### Authentication APIs



| Method | Endpoint | Description |

|---|---|---|

| POST | `/api/auth/signup` | Register a new user |

| POST | `/api/auth/login` | Authenticate an existing user |



\*\*Signup Request Example:\*\*



```json

{

&#x20; "name": "Your Name",

&#x20; "email": "your@email.com",

&#x20; "password": "yourpassword",

&#x20; "phone": "9876543210"

}

```



\*\*Login Request Example:\*\*



```json

{

&#x20; "email": "your@email.com",

&#x20; "password": "yourpassword"

}

```



Successful login returns a JWT token.



\### Transaction APIs



These endpoints require authentication.



| Method | Endpoint | Description |

|---|---|---|

| POST | `/api/transactions` | Add a transaction |

| GET | `/api/transactions` | Retrieve logged-in user's transactions |

| GET | `/api/transactions/{id}` | Retrieve a transaction by ID |

| PUT | `/api/transactions/{id}` | Update a transaction |

| DELETE | `/api/transactions/{id}` | Delete a transaction |



\### User Profile APIs



These endpoints require authentication.



| Method | Endpoint | Description |

|---|---|---|

| GET | `/api/users/me` | Retrieve logged-in user's profile |

| PUT | `/api/users/me` | Update logged-in user's profile |



\### Authentication Header



For protected endpoints, include the JWT token in the request header:



```text

Authorization: Bearer YOUR\_JWT\_TOKEN

```



\## 🔐 Security



\- Spring Security manages authentication and protected endpoints.

\- JWT tokens are generated after successful login.

\- BCrypt is used to encrypt user passwords.

\- Protected APIs use the authenticated user's identity.

\- Database credentials and JWT secrets are excluded from version control.



\## 🌐 Frontend Integration



The backend is designed to work with the SpendWise React frontend.



\*\*Frontend Repository:\*\*  

https://github.com/DarillynDarsha/SpendWise



The local frontend development server runs on:



```text

http://localhost:5173

```



\## 👩‍💻 Developer



\*\*Darillyn Darsha S\*\*  

Information Technology Student  

Aspiring Full Stack Developer



\---



⭐ If you find this project interesting, feel free to explore the repository!


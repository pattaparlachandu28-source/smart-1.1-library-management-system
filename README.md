# Smart Library Management System

A full-stack college library application built with React, Spring Boot, REST APIs, and MySQL. Librarians manage the catalogue, members, circulation, fines, and reports. Students browse and borrow books, track due dates, and receive explainable recommendations from library borrowing data.

## Stack

- React 19 + Vite frontend
- Java 17+ / Spring Boot 3.4 backend with Spring Data JPA and Hibernate
- REST API (`/api/...`)
- MySQL 8+
- Maven

## Run locally

1. Run `mysql -u root -p < backend/schema.sql` in Command Prompt to create the database, then set `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` if your local MySQL credentials differ from the defaults.
2. Start the API:

   ```powershell
   cd backend
   mvn spring-boot:run
   ```

   The API runs at `http://localhost:8080`. Hibernate creates and updates tables on startup. At startup, the app fills in any missing titles from a 24-book starter catalogue with available copies; it does not invent loan or activity history. Public registration creates student accounts. To bootstrap the first librarian in Command Prompt, stop the backend if it is running, then set the key and start it in the same window:

   ```cmd
   set LIBRARIAN_SETUP_KEY=ChooseYourOwnSetupKey
   mvn spring-boot:run
   ```

   In the app, use **Create account** and enter that setup key to create your librarian account. Registration signs you in. Students create accounts with the same form, leaving the key blank. The same sign-in form works for both roles and opens the correct dashboard. Librarians can create more student accounts through **Students**.

3. Start the React client in another terminal:

   ```powershell
   cd frontend
   npm install
   npm run dev
   ```

   Visit `http://localhost:5173`. Vite proxies `/api` to Spring Boot. The browser stores the signed bearer token locally; logout removes it.

## Main API

| Method | Endpoint | Access |
|---|---|---|
| POST | `/api/auth/register` | Public student registration; first librarian requires setup key |
| POST | `/api/auth/login` | Public |
| GET | `/api/books?q=&category=&page=&size=` | Signed in |
| POST, PUT, DELETE | `/api/books[/{id}]` | Librarian |
| GET | `/api/dashboard` | Signed in; role-specific data |
| GET | `/api/transactions` | Student's own history / librarian all history |
| POST | `/api/transactions/issue` | Student self-service / librarian on behalf of member |
| POST | `/api/transactions/{id}/return` | Owning student / librarian |
| GET | `/api/recommendations` | Student |
| GET, POST | `/api/members` | Librarian |

Borrowing is transactional, locks inventory rows while issuing/returning, enforces the per-member limit, and calculates fines from `LIBRARY_FINE_PER_DAY` (default ₹5). Recommendations use the student's borrowing categories and real circulation popularity; they include a short reason and omit unavailable books. Passwords are BCrypt hashed. API authorization is enforced on the server.

## Configuration

Defaults target local MySQL: database `smart_library_spring`, username `root`, password `root`. This database name keeps the Spring Boot schema separate from any existing `smart_library` database. Configure `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`, `JWT_EXPIRY_HOURS`, `LIBRARIAN_SETUP_KEY`, `LIBRARY_LOAN_DAYS`, `LIBRARY_BORROW_LIMIT`, and `LIBRARY_FINE_PER_DAY` as needed. Set a long random `JWT_SECRET` and a private setup key before deployment.

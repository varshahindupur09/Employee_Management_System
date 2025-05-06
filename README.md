# Flywire-Exercise

## Overview

This is a Spring Boot web service that manages and retrieves company employee information. The application uses a `data.json` file as its data source. The service provides RESTful endpoints for common employee operations and includes a frontend React UI to display employee data.

---

## Tech Stack

- **Backend**: Spring Boot (Java), Apache Tomcat
- **Frontend**: React, Vite, NextUI (or Hero UI)
- **Data Storage**: JSON file-based persistence
- **Build Tool**: Maven

---

## Setup Instructions

### Backend

1. **Clone the repository:**

   ```bash
   git clone https://github.com/yourusername/flywire-exercise.git
   cd flywire-exercise-main

## Screenshot of the UI:
<img width="1440" alt="image" src="https://github.com/user-attachments/assets/2edd0604-2735-4db4-a506-d0ffa5d136c9" />

## Screenshot of Backend Testing using CURL command:
<img width="857" alt="image" src="https://github.com/user-attachments/assets/7a2d33e1-ba1e-45a3-b267-439a33357e64" />
<img width="847" alt="image" src="https://github.com/user-attachments/assets/d08e899d-a36a-4a8b-944b-e9b94a40cf2d" />

## API Endpoints:
-> curl http://localhost:8080/api/employees/active <br />
-> curl http://localhost:8080/api/employees/inactive <br />
-> curl http://localhost:8080/api/employees/1 <br />
-> curl "http://localhost:8080/api/employees/hired?startDate=01/01/2015&endDate=12/31/2020" <br />
-> curl -X POST http://localhost:8080/api/employees <br />
-H "Content-Type: application/json" <br />
-d '{ "id": 203, "name": "Jason Aqua", "position": "Marketing Lead", "directReports": [24, 129] }' <br />
-> curl -X DELETE http://localhost:8080/api/employees/132 <br />





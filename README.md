# SportsClubManagement

Sports Club Management System is a full-stack Java Servlet based web application designed to streamline athlete registration, profile management, and sports administration.

The project provides a modern athlete onboarding system with secure database integration, document uploads, admin authentication, and an interactive admin dashboard for managing athlete records efficiently.

---

# Features

## Athlete Registration Module

* Multi-step athlete registration form
* Personal details collection
* Guardian information management
* Address information storage
* Sports profile registration
* Document upload support
* Auto age calculation from DOB
* Form validation using JavaScript

---

## Document Upload System

Supports secure upload of:

* Athlete Photo
* Birth Certificate
* ID Proof

### File Validation

* Image formats: JPG / PNG
* PDF support
* File size restrictions
* Server-side upload handling using Servlet `Part`

---

## Admin Module

### Secure Admin Login

* Authentication using Oracle Database
* Session-based access handling

### Admin Dashboard

* View all registered athletes
* Dynamic athlete data table
* Athlete status management
* Responsive dashboard UI

### Athlete Profile View

Detailed athlete profile page containing:

* Athlete Information
* Guardian Details
* Address Information
* Sports Profile
* Uploaded Documents
* Approval Status

---

# Technologies Used

## Frontend

* HTML5
* CSS3
* JavaScript

## Backend

* Java Servlets
* JDBC

## Database

* Oracle Database

## Server

* Apache Tomcat

---

# Database Structure

The project uses a relational database architecture with proper foreign key relationships.

## Tables

* ATHLETE
* GUARDIAN
* ADDRESS
* SPORTS_PROFILE
* DOCUMENTS
* ADMIN1

---

# Database Relationships

ATHLETE is the parent table connected with:

* GUARDIAN
* ADDRESS
* SPORTS_PROFILE
* DOCUMENTS

using foreign key constraints with `ON DELETE CASCADE`.

---

# Project Modules

## 1. Athlete Registration

Handles:

* Athlete data insertion
* Validation
* File upload processing
* Dynamic ID generation

## 2. DAO Layer

Responsible for:

* Database connectivity
* SQL operations
* Transaction management
* Rollback handling

## 3. Admin Authentication

Provides:

* Secure login
* Credential verification
* Dashboard access control

## 4. Dashboard Management

Allows admin to:

* View athletes
* Verify registrations
* Access uploaded documents
* Monitor registration status

---

# Folder Structure

```plaintext
SportsClubManagement/
│
├── src/
│   ├── controller/
│   ├── DAO/
│   ├── model/
│   ├── util/
│   └── db/
│
├── web/
│   ├── css/
│   ├── js/
│   ├── athlete_docs/
│   └── html pages
│
└── README.md
```

---

# Key Functionalities

## Dynamic Athlete ID Generation

Custom ID generation utilities for:

* Athlete ID
* Guardian ID
* Address ID
* Sports Profile ID
* Document ID

---

# UI Design

The project uses:

* Modern SaaS inspired layout
* Responsive cards
* Animated transitions
* Professional admin dashboard
* Athlete profile management interface

---

# How to Run the Project

## Requirements

* JDK 8+
* Apache Tomcat
* Oracle Database
* NetBeans / Eclipse

---

## Setup Steps

### 1. Clone Repository

```bash
git clone <repository-url>
```

### 2. Configure Database

Create all required Oracle tables.

### 3. Configure JDBC Connection

Update database credentials inside:

```java
DbConnection.java
```

### 4. Deploy on Tomcat

Run the project using Apache Tomcat server.

---

# Admin Login

Admin credentials are stored inside:

```sql
ADMIN1
```

Example:

```sql
INSERT INTO ADMIN1
VALUES (1,'admin','admin123',SYSDATE);
```

---

# Future Enhancements

* Athlete approval workflow
* Export athlete data to PDF
* Email notifications
* Competition management
* Coach management
* JWT authentication
* Role-based access
* Cloud document storage

---

# Developer

Developed by Suvam Chowdhury

---

# License

This project is developed for educational and evaluation purposes.

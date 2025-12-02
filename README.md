# Recipes Web Application

A full-stack recipe management application where users can create, store, and browse cooking recipes. Built as a university project for Szoftverfejlesztési módszertanok by  
Kovács Veronika, Vesa Bence, Kulcsár Gerzson.

---

## Purpose of the Application

The Recipes App allows users to:

- Add and store their own recipes  
- Browse recipes uploaded by others  
- View detailed descriptions and required ingredients  
- Manage their uploaded content  
- Access admin-level management functions (for admin users)

---

## Project Management – Trello

The Trello board included:

- To Do  
- In Progress  
- To Accept  
- Done  
- Epics  

Ticket examples:
- Recipe Controller – methods handling recipe endpoints  
- User Service – business logic  
- Epics – large features broken into tasks

---

# Backend (Java + Spring Boot)

## Tech Stack
- Java 21  
- Spring Boot  
- PostgreSQL  
- Spring Security + JWT  
- Checkstyle  
- Jacoco (73% test coverage)

---

## Models

### User
- Stores user details  
- A user can have multiple recipes (1-N relationship)

### Recipe
- Depends on User  
- Contains ingredients and description

---

## Services

### Recipe Service
- Repository  
- JWT service  
- Model–DTO converter  

### User Service
- User-related business logic  

---

## Controllers

### Recipe Controller
- Handles recipe endpoints  
- Method-level authorization applied  

### User Controller
- Registration  
- Login  
- User operations  

---

## Security (JWT)

- Endpoints secured with role-based access  
- JWT token validation via custom filters  
- Token validity ~30 minutes  

---

# Testing

- Unit tests implemented  
- Test coverage via Jacoco  
- Total: 73%

---

# Frontend (React + TypeScript)

## Tech Stack
- React  
- TypeScript  
- Tailwind CSS  
- Zustand  
- TanStack Query  
- React-Toastify  

## Features
- Form validation on login and registration  
- Logout invalidates token  
- Dynamic welcome message showing logged-in user  

---

# Main Pages

## Homepage
- General information  
- Logout button invalidates token  

## My Recipes
- Shows recipes uploaded by the logged-in user  
- Add, edit, delete recipes  

## All Recipes
- All recipes from every user  
- Users can only modify their own recipes  
- Admins can edit or delete any recipe  
- Users can also add new recipes here  

## Admin Panel
- View all users  
- Delete users  
- Grant admin roles  
- Admin cannot delete themselves  
- Admin can edit or delete any recipe  

---

# Running the App (Docker)

```bash
docker-compose up --build

# 🏋️ WorkoutApp

A full-stack fitness tracking application with an **Android frontend
(Kotlin)** and a **NestJS backend**, designed to help users track
workouts, health metrics, and daily activity in a clean, modern
interface.

------------------------------------------------------------------------

## 📱 Features

### 🔹 Mobile App (Android - Kotlin)

-   📊 Dashboard with fitness metrics (Calories, Steps, Heart Rate,
    Water, Workout Time)
-   📅 Calendar-based workout/task tracking
-   👤 User profile management
-   🔐 Authentication (Login/Register)
-   🌙 Light/Dark mode support
-   📜 Terms & Conditions flow
-   💾 Local session management using SharedPreferences

------------------------------------------------------------------------

### 🔹 Backend (NestJS)

-   🔑 Authentication APIs
-   👤 User management
-   📊 Metrics configuration (name, unit, icon, color)
-   📅 Task/Workout tracking APIs
-   🗄️ Database-driven configuration for frontend rendering

------------------------------------------------------------------------

## 🧱 Tech Stack

### Frontend (Android)

-   Kotlin
-   XML Layouts (Material 3)
-   RecyclerView
-   Fragments
-   SharedPreferences (AppPrefs)
-   Retrofit (API calls)

### Backend

-   NestJS
-   TypeScript
-   REST APIs
-   SQL Database

------------------------------------------------------------------------

## 📁 Project Structure

    WorkoutApp/
    │
    ├── app/                    # Android App (Kotlin)
    │   ├── activities/
    │   ├── fragments/
    │   ├── adapters/
    │   ├── models/
    │   ├── prefs/             # AppPrefs (local storage)
    │   ├── api/               # Retrofit services
    │   └── res/
    │
    ├── backend/               # NestJS Backend
    │   ├── src/
    │   ├── modules/
    │   ├── controllers/
    │   ├── services/
    │   └── entities/
    │
    └── README.md

------------------------------------------------------------------------

## ⚙️ Setup Instructions

### 📱 Android App

1.  Open project in Android Studio
2.  Sync Gradle
3.  Run on emulator or device

------------------------------------------------------------------------

### 🖥️ Backend (NestJS)

    cd backend
    npm install
    npm run start:dev

------------------------------------------------------------------------

## 🔌 API Integration

The Android app communicates with the backend using Retrofit.

------------------------------------------------------------------------

## 🧠 Architecture Overview

### Frontend

-   UI handled via Fragments
-   API logic abstracted via Repositories
-   Local state managed using AppPrefs

### Backend

-   Modular NestJS structure
-   Controllers → Services → Database

------------------------------------------------------------------------

## 🎯 Key Design Decisions

-   Backend controls metadata (name, unit, icon, color)
-   Frontend generates temporary metric values
-   Repository pattern for clean separation
-   Material 3 UI
-   Dark mode support

------------------------------------------------------------------------

## 🚀 Future Improvements

-   Real-time tracking
-   Charts & analytics
-   Cloud sync
-   Notifications
-   AI workout suggestions

------------------------------------------------------------------------

## 👨‍💻 Author

Abdur Rehman Malik\
BS Software Engineering @ FAST-NU Lahore

------------------------------------------------------------------------

## 📄 License

This project is licensed under the **Apache License 2.0**.

### Apache License Summary

-   You are free to use, modify, and distribute this software
-   You must include the original license and copyright notice
-   The software is provided "as is", without warranty of any kind

For full license details, see:
http://www.apache.org/licenses/LICENSE-2.0

------------------------------------------------------------------------

© 2026 Abdur Rehman Malik

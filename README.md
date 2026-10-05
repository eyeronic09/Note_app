# Momentum Note App

Momentum is a feature-rich note-taking and to-do application built with modern Android development practices. It provides a seamless experience for managing tasks, capturing ideas, and synchronizing data across devices using Firebase.

The app features a rich text editor, image attachments, categorizations, and robust offline support, ensuring that you can remain productive regardless of your network connection.

## 📱 Features

### Note Taking
* **Rich Text Editing:** Format your notes seamlessly.
* **Image Support:** Attach images to your notes using Coil for image loading.
* **Organization:** Pin important notes, archive completed ones, and categorize notes efficiently.
* **Offline First:** Write and manage notes without an internet connection. Changes sync automatically in the background using WorkManager once connectivity is restored.
* **Search:** Search across note titles and contents quickly.

### Todo Management
* **Calendar View:** Manage tasks directly from an interactive calendar interface.
* **Scheduling:** Set deadlines and view tasks by date.
* **Notifications:** Receive scheduled local notifications for upcoming to-dos.

### Authentication & Cloud Sync
* **Google Sign-In & Email/Password:** Secure authentication via Firebase and Jetpack Credential Manager.
* **Cross-Device Sync:** Firebase Firestore ensures your data is always up-to-date across multiple devices.

## 🛠 Tech Stack

* **Language:** [Kotlin](https://kotlinlang.org/)
* **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3 UI, Compose Navigation, Coil)
* **Architecture:** Clean Architecture with MVVM (Model-View-ViewModel)
* **Dependency Injection:** [Koin](https://insert-koin.io/)
* **Local Database:** [Room](https://developer.android.com/training/data-storage/room)
* **Background Processing:** [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)
* **Backend & Auth:** [Firebase](https://firebase.google.com/) (Firestore, Auth, Storage, Analytics, Crashlytics)
* **Navigation:** Voyager

## 📂 Project Structure

The project is structured into logical feature modules and layers (Domain, Data, UI):

*   **`HomeScreen`:** Manages Notes, Archiving, and the Rich Editor. Contains Data layer (Room, Firebase), Domain Layer (UseCases, Models), and Presentation Layer (ViewModels, Compose UI).
*   **`TodoFeature`:** Handles Todo creation, calendar integration, scheduling, and Notifications.
*   **`sign_in`:** Manages Authentication workflows, user state, and Google Sign-in integration.

## 🚀 Getting Started

### Prerequisites
*   [Android Studio](https://developer.android.com/studio) (Latest version recommended)
*   JDK 17 or higher
*   A Firebase Project

### Setup Instructions

1.  **Clone the Repository**
    ```bash
    git clone https://github.com/yourusername/Momentum.git
    cd Momentum
    ```

2.  **Firebase Setup**
    *   Go to the [Firebase Console](https://console.firebase.google.com/).
    *   Create a new project and add an Android app.
    *   Register your app using the package name `com.example.noteapp`.
    *   Download the `google-services.json` file and place it in the `app/` directory.
    *   Enable **Authentication** (Email/Password & Google Sign-In).
    *   Enable **Firestore Database** and set up appropriate security rules.
    *   Enable **Storage** (if you intend to sync images).

3.  **Google Sign-In Requirements**
    *   To make Google Sign-In work, ensure you've added your SHA-1 and SHA-256 fingerprint hashes in the Firebase console settings for your Android app.
    *   You will need to update the `default_web_client_id` in your `strings.xml` or pass it securely depending on your Firebase setup.

4.  **Build and Run**
    *   Open the project in Android Studio.
    *   Wait for Gradle to sync.
    *   Select an emulator or a connected physical device and click **Run**.

## 🤝 Contributing
Contributions, issues, and feature requests are welcome!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)

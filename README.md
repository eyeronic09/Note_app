# Momentum

**Momentum** is a modern, feature-rich Android application designed to help you stay organized with both notes and tasks. Built with Jetpack Compose and following Clean Architecture principles, it offers a seamless and intuitive experience for managing your daily life.

## 🚀 Features

### 📝 Notes Management
- **Rich Note Taking**: Create and edit notes with titles and content.
- **Search**: Quickly find specific notes using the integrated search functionality.
- **Image Attachments**: Attach photos to your notes for more context.
- **Organization**: Sort notes by date, title, or custom order.

### ✅ Todo & Task Management
- **Task Tracking**: Create tasks with descriptions, due dates, and specific times.
- **Priority Levels**: Categorize tasks as Low, Medium, or High priority.
- **Visual Progress**: See your "To Do" vs "Done" status at a glance with statistics cards.
- **Calendar Integration**: A beautiful week-view calendar to navigate through your schedule.
- **Reminders**: Get notified about your upcoming tasks via system notifications.

## 🛠 Tech Stack

- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) for a modern, declarative UI.
- **Architecture**: Clean Architecture (Domain, Data, UI) with MVI/MVVM patterns.
- **Dependency Injection**: [Koin](https://insert-koin.io/) for lightweight and powerful DI.
- **Database**: [Room](https://developer.android.com/training/data-storage/room) for robust local data persistence.
- **Navigation**: [Voyager](https://voyager.adriel.cafe/) for multi-platform navigation.
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/) for fast and asynchronous image loading.
- **Calendar**: [Compose-Calendar](https://github.com/kizitonwose/Calendar) for the week-view implementation.
- **Background Tasks**: AlarmManager for precise notification scheduling.

## 🏗 Project Structure

The project is organized into clear layers to ensure maintainability and scalability:

```text
com.example.noteapp
├── HomeScreen          # Notes feature
│   ├── data_layer      # Room DB, Repository implementations
│   ├── domain_layer    # Models, Use Cases, Repository interfaces
│   └── Ui_prestentionLayer # ViewModels and Compose Screens
└── TodoFeature         # Tasks feature
    ├── HomeScreen      # Todo Dashboard & Calendar
    ├── AddScreen       # Task creation and editing
    ├── Todo_Notification # Notification scheduling logic
    └── Utilty          # Formatting and UI helpers
```

## ⚙️ Setup & Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/Momentum.git
   ```
2. Open the project in **Android Studio (Ladybug or newer)**.
3. Sync the project with Gradle files.
4. Run the application on an emulator or a physical device (API 26+ recommended for notification features).

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

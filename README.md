# 📝 ComposeTodoApp

> A modern Android Todo application with full **CRUD** support, built with **Jetpack Compose** and **Room Database**.

---

## 📖 Description

**ComposeTodoApp** is an Android application that lets you manage your daily tasks with ease. It supports creating, viewing, updating, and deleting todo items, with all data persisted locally using the **Room** library. The UI is built entirely with **Jetpack Compose**, following a reactive, state-driven approach.

---

## ✨ Features

- ✅ **Add Todos** — Create new todo items with a title and description
- 📋 **View Todos** — See all your todos in a scrollable list
- ✏️ **Update Todos** — Edit existing todo items
- 🗑️ **Delete Todos** — Remove todos you no longer need
- 💾 **Persistent Storage** — All todos are saved locally using Room Database, surviving app restarts
- 🕐 **Timestamps** — Each todo records the date and time it was created
- 🌙 **Material Design 3** — Clean, modern UI with dynamic theming support

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| [Kotlin](https://kotlinlang.org/) | Primary programming language |
| [Jetpack Compose](https://developer.android.com/jetpack/compose) | Declarative UI framework |
| [Room Database](https://developer.android.com/training/data-storage/room) | Local data persistence & CRUD operations |
| [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) | Asynchronous database operations |
| [Kotlin Flow](https://kotlinlang.org/docs/flow.html) | Reactive data streams from the database |
| [Material Design 3](https://m3.material.io/) | UI components and theming |
| [KSP](https://github.com/google/ksp) | Kotlin Symbol Processing for Room code generation |

---

## 🏗️ Architecture

The app follows a simplified **MVVM** (Model-View-ViewModel) inspired architecture with clear separation of concerns:

```
┌─────────────────────┐
│     UI Layer        │  Jetpack Compose screens & components
│  (TodoScreen.kt)    │
└────────┬────────────┘
         │ collectAsState()
┌────────▼────────────┐
│    Data Layer       │  Room Database (DAO + Entity + Database)
│  (TodoDAO.kt,       │  Flow<List<Todo>> for reactive updates
│   TodoDatabase.kt)  │
└─────────────────────┘
```

- **Entity:** `Todo` — data class annotated with `@Entity` for Room
- **DAO:** `TodoDAO` — defines `upsert`, `delete`, and `getAllTodos()` (returning `Flow`)
- **Database:** `TodoDatabase` — Room singleton with schema migration support (v1 → v4)
- **UI:** Compose observes the `Flow` via `collectAsState()` for real-time updates

---

## 🚀 Getting Started

### Prerequisites

- [Android Studio](https://developer.android.com/studio) (Hedgehog or newer recommended)
- Android SDK with **API level 26** (Android 8.0) or higher
- JDK 11 or higher

### Clone the Repository

```bash
git clone https://github.com/Shoaibkhalid65/ComposeTodoApp.git
cd ComposeTodoApp
```

### Build & Run

1. Open the project in **Android Studio**
2. Let Gradle sync and download all dependencies
3. Connect a device or start an emulator (API 26+)
4. Click **▶ Run** or use the shortcut `Shift + F10`

### SDK Versions

| Setting | Value |
|---|---|
| Minimum SDK | 26 (Android 8.0 Oreo) |
| Target SDK | 36 (Android 15) |
| Compile SDK | 36 |

---

## 📁 Project Structure

```
app/
└── src/
    └── main/
        └── java/com/example/finaltermdatabase/
            ├── MainActivity.kt          # App entry point, sets up Compose host
            ├── TodoScreen.kt            # Main UI screen (list, FAB, dialog)
            ├── TodoData.kt              # Todo entity + type converters
            ├── TodoDAO.kt               # Data Access Object (CRUD operations)
            ├── TodoDatabase.kt          # Room database & migration definitions
            └── ui/theme/
                ├── Color.kt             # App color palette
                ├── Theme.kt             # Material3 theme configuration
                └── Type.kt              # Typography definitions
```

---

## 📸 Screenshots

> _Screenshots coming soon!_

| Todo List | Add Todo | Delete Todo |
|---|---|---|
| _(placeholder)_ | _(placeholder)_ | _(placeholder)_ |

---

## 🧪 Testing

The project includes both unit and instrumented tests:

| Test | Type | Description |
|---|---|---|
| `ExampleUnitTest` | Unit Test | Basic sanity check |
| `ExampleInstrumentedTest` | Instrumented Test | Verifies app context |
| `TodoMigrationTesting` | Database Test | Validates Room schema migrations |

Run tests via Android Studio or with Gradle:

```bash
# Unit tests
./gradlew test

# Instrumented tests (requires connected device/emulator)
./gradlew connectedAndroidTest
```

---

## 📄 License

This project currently has **no license specified**. All rights reserved by the author unless otherwise stated.

---

## 👤 Author

**Shoaibkhalid65** — [GitHub Profile](https://github.com/Shoaibkhalid65)

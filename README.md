# Vehicle Dashboard

An Android app for viewing a vehicle list and individual vehicle details using the configured MockAPI service.

## Features

- Vehicle list with name, model, battery percentage, estimated range, and online/offline status.
- Vehicle details with current speed, odometer, connectivity, last updated time, and refresh.
- Loading, empty, error, and retry states.

## Build and run

Open the project in Android Studio and run the `app` configuration on an emulator or Android device. The app requires internet access to load vehicle data.

To build and run the local unit tests from the project root:

```bash
./gradlew assembleDebug testDebugUnitTest
```

## MVVM architecture

The app uses Kotlin, Jetpack Compose, Material 3, Coroutines, and StateFlow. Compose Navigation connects the vehicle list and details screens.

- **View:** `VehicleListScreen` and `VehicleDetailsScreen` render UI state and send user actions to their ViewModels. They collect `StateFlow` with lifecycle-aware collection.
- **ViewModel:** `VehicleListViewModel` and `VehicleDetailsViewModel` load or refresh vehicles in `viewModelScope` and expose immutable UI state to Compose.
- **Model/data:** The domain layer contains the `Vehicle` model and `VehicleRepository` contract. `VehicleRepositoryImpl` obtains data from `VehicleService` and maps API DTOs to domain models. Retrofit and Gson handle REST calls and JSON conversion.
- **Dependency injection:** Hilt provides the Retrofit service and binds the repository implementation to its domain interface.

## Assumptions and limitations

- The configured MockAPI endpoint is used as the data source and must remain reachable for live data.
- API responses are expected to provide the vehicle fields represented by `VehicleDto`.
- Vehicle data is not cached locally; a network connection is required.

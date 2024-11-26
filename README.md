# Cities App

Citiess App shows a list of cities with their respective data such as: city name, country and coordinates. You can also perform a search by the name of the city.

## Features
- Search cities by name.
- See city coordinates and location on the map (Google Maps)
- Dynamic UI that follows the wireframes. Hence, when in portrait different screens are used for the list and map but when in landscape, a single screen is used.
- Data is obtained from: ["cities.json"](https://gist.githubusercontent.com/hernan-uala/dce8843a8edbe0b0018b32e137bc2b3a/raw/0996accf70cb0ca0e16f9a99e0ee185fafca7af1/cities.json)

## Architecture

This project follows the MVVM and Clean Architecture architecture pattern.

### MVVM (Model-View-ViewModel)

- **Model**: Manages business and data access logic.
- **View**: Handles the user interface logic. In this project it would be the CitiesListScreen.
- **ViewModel**: Handles data related to the UI. Provides data to the View and reacts to user interactions.

### Clean Architecture
<img src="/images/clean_architecture.jpg" align="center" width="500"/>

- **Data Layer**: Handles data operations. Contains repositories and the remote data source (API).
- **Domain Layer**: Contains the business logic and use cases.
- **Presentation Layer**: Contains the presentation logic and interaction with the UI.


## Technologies and libraries used
- **Kotlin**: Main programming language.
- **Hilt**: For dependency injection.
- **Retrofit**: For API calls.
- **Coroutines**: To handle asynchronous tasks.
- **Flow**: For reactive programming.
- **MockK**: For unit tests.
- **JUnit**: Test framework.


## Prerequisites

- Android Studio Arctic Fox or higher.
- JDK 8 or higher.

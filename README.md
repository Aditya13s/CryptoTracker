# CryptoTracker

CryptoTracker is an Android application designed to provide users with real-time information about various cryptocurrencies. It offers a user-friendly interface for tracking cryptocurrency prices, complete with auto-refresh and pull-to-refresh functionalities.

## Features

1. **Currency List:**
   - Displays a comprehensive list of cryptocurrencies with full names, icons, and exchange rates rounded off to 6 decimal places.

2. **Refresh Functionality:**
   - Swipe-to-refresh for immediate updates.
   - Auto-refreshes data every 3 minutes.
   - Last refresh time is prominently displayed on the UI.

## Technologies and Libraries Used

- **ViewModel and LiveData:** Follows the MVVM architecture using Android's ViewModel and LiveData for efficient data handling.
- **Coroutines:** Leverages Kotlin Coroutines for seamless asynchronous programming, ensuring concise and readable code.
- **Hilt Dependency Injection:** Utilizes Dagger Hilt for streamlined dependency injection, simplifying the management of dependencies in the app.
- **RecyclerView:** Implements RecyclerView to efficiently display a scrollable list of cryptocurrencies.
- **SwipeRefreshLayout:** Incorporates the SwipeRefreshLayout component for an intuitive pull-to-refresh user experience.

## External Libraries

- [Hilt](https://dagger.dev/hilt/): Dependency Injection library.
- [Retrofit](https://square.github.io/retrofit/): HTTP client for seamless API communication.
- [Glide](https://github.com/bumptech/glide): Image loading library for displaying cryptocurrency icons.

## Demo Video
https://github.com/Aditya13s/CryptoTracker/assets/77337791/f7b18a1f-4b98-48ce-979a-562c5ffabf05


# Infinite scroll sample

This project demonstrates how to create an infinite scroll of fetched items.
This project will be using [IMDB public API](https://developers.themoviedb.org/3/getting-started/introduction) in order to obtain a list of popular TV shows, 
then it will show them in a scrollable list.
The load will be page-based, an initial set of items will be retrieved and when the user
scrolls down then another page of results will be dynamically loaded and added to the scroll.

## Contents of this sample

- Android development with Kotlin and [AndroidX](https://developer.android.com/jetpack/androidx) libraries.
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) with [Model-View-Presenter](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter) for the presentation layer.
- Dependency injection with [Koin](https://insert-koin.io/).
- Reactive programming with [RxKotlin](https://github.com/ReactiveX/RxKotlin) & [RxAndroid](https://github.com/ReactiveX/RxAndroid).
- Networking with [Retrofit](https://square.github.io/retrofit/).
- Image caching with [Glide](https://github.com/bumptech/glide).
- Development of custom widgets.
- Unit tests with [Mockito](https://github.com/mockito/mockito).
- Instrumentation tests with [Espresso](https://developer.android.com/training/testing/espresso).

## How to run this sample

You must have a valid API key of the [IMDB public API](https://developers.themoviedb.org/3/getting-started/introduction) in order to run this sample app. Once you 
have the key, please follow this steps to run this project in your device or emulator:

- Download the source code
- Create a file in the root directory called apikey.properties
- Create a new line with the following content, substituting <your_key> for you valid API key: `IMDB_API_KEY="<your_key>"`
- Now you are ready to build and execute the app.

## Image caching

We are going to load a small thumbnail of each element. Two main libraries were analyzed: [Picasso](https://square.github.io/picasso/) and [Glide](https://github.com/bumptech/glide). We are going to use Glide because it stores the preview pictures with the final image size, hence the loading from cache should be faster.

## Unit tests

Some sample unit tests will be written for this project, for that purpose we are going to use the following tools:
- [Mockito](https://github.com/mockito/mockito) as our mock library. 
- [HierarchicalContextRunner](https://mvnrepository.com/artifact/de.bechte.junit/junit-hierarchicalcontextrunner) which will allow us to write WHEN-GIVEN-SHOULD style tests.

## Instrumentation (UI) tests

This project contains [Espresso](https://developer.android.com/training/testing/espresso) tests to ensure that the views works as expected. **Please ensure to deactivate the animations in your test device in order to avoid false negatives.**
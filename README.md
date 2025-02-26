# The Movie
The Movie App is an application that use for easiest way to find discover movie, actor and tv shows.

## Introduction
The application uses Clean Architecture based on MVVM and Repository patterns. Implemented
Architecture principles follow Google recommended [Guide to app architecture](https://developer.android.com/jetpack/docs/guide).

![Guide to app architecture](screenshots/guide-to-app-architecture.png "Guide to app architecture")

## Libraries used

The application goal is to show case current Android Architecture state using out of box
Android tools made by Google (Android Jetpack) and 3rd party community driven libraries.

Android Jetpack is a set of components, tools and guidance to make great Android apps. They bring
together the existing Support Library and Architecture Components and arranges them into four
categories:

![Android Jetpack](screenshots/jetpack_donut.png "Android Jetpack Components")

* [Foundation][0] - Components for core system capabilities, Kotlin extensions and support for multidex and automated testing.
  * [AppCompat][1] - Degrade gracefully on older versions of Android.
  * [Android KTX][2] - Write more concise, idiomatic Kotlin code.
* [Architecture][3] - A collection of libraries that help you design robust, testable, and maintainable apps. Start with classes for managing your UI component lifecycle and handling data persistence.
  * [Data Binding][4] - Declaratively bind observable data to UI elements.
  * [Lifecycles][5] - Create a UI that automatically responds to lifecycle events.
  * [LiveData][6] - Build data objects that notify views when the underlying database changes.
  * [Navigation][7] - Handle everything needed for in-app navigation.
  * [Room][8] - SQLite database with in-app objects and compile-time checks.
  * [ViewModel][9] - Store UI-related data that isn't destroyed on app rotations. Easily schedule asynchronous tasks for optimal execution.
  * [Paging][10] - Load and display small chunks of data at a time.
* [UI][11] - Details on why and how to use UI Components in your apps - together or separate.
  * [Fragment][12] - A basic unit of composable UI.
  * [Layout][13] - Layout widgets using different algorithms.
  * [Material][14] - Material Components.
* Third party
  * [Kotlin Coroutines][15] for managing background threads with simplified code
     and reducing needs for callbacks.
  * [Dagger 2][16] A fast dependency injector.
  * [Retrofit 2][17] A configurable REST client.
  * [OkHttp 3][18] A type-safe HTTP client.
  * [GSON][19] A Json - Object converter using reflection.
  * [Glide][20] Image loading.
  * [Timber][21] A logger.

[0]: https://developer.android.com/jetpack/components
[1]: https://developer.android.com/topic/libraries/support-library/packages#v7-appcompat
[2]: https://developer.android.com/kotlin/ktx
[3]: https://developer.android.com/jetpack/arch/
[4]: https://developer.android.com/topic/libraries/data-binding/
[5]: https://developer.android.com/topic/libraries/architecture/lifecycle
[6]: https://developer.android.com/topic/libraries/architecture/livedata
[7]: https://developer.android.com/topic/libraries/architecture/navigation/
[8]: https://developer.android.com/topic/libraries/architecture/room
[9]: https://developer.android.com/topic/libraries/architecture/viewmodel
[10]: https://developer.android.com/topic/libraries/architecture/paging
[11]: https://developer.android.com/guide/topics/ui
[12]: https://developer.android.com/guide/components/fragments
[13]: https://developer.android.com/guide/topics/ui/declaring-layout
[14]: https://material.io/develop/android/docs/getting-started/
[15]: https://kotlinlang.org/docs/reference/coroutines-overview.html
[16]: https://dagger.dev/users-guide
[17]: https://square.github.io/retrofit/
[18]: https://square.github.io/okhttp/
[19]: https://github.com/google/gson
[20]: https://bumptech.github.io/glide/
[21]: https://github.com/JakeWharton/timber
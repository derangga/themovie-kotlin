# The Movie
Aplikasi list Movie dengan data source dari Themovie DB

## Aplikasi ini dibuat dengan menggunakan
- Retrofit2
- RxKotlin2 dan RxAndroid
- Room (for local database)
- Kotlin Anko
- Android Architecture Component
- MVVM Pattern
- Dagger 2

## Issue
- 14-10-2019 : function `create()` pada DataSource selalu dijalankan ketika menjalankan function `invalidate()` pada `ViewModel`
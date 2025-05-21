package com.android.animeapp.presentation.navigation

sealed class Routes(val route: String) {
    object HomeScreen: Routes("home")
    object AnimeCharacterDetailScreen: Routes("animeChar")
    object PlanetDetailScreen: Routes("planet_screen")
}
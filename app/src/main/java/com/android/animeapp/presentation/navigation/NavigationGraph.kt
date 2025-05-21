package com.android.animeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.animeapp.presentation.screens.AnimeCharacterDetailScreen
import com.android.animeapp.presentation.screens.HomeScreen
import com.android.animeapp.presentation.screens.PlanetDetailScreen
import com.android.animeapp.presentation.viewmodel.AnimeViewModel

@Composable
fun NavigationGraph(navController: NavHostController, viewModel: AnimeViewModel = hiltViewModel()){
    NavHost(navController = navController, startDestination = Routes.HomeScreen.route) {
        composable(Routes.HomeScreen.route) {
            HomeScreen(navController,viewModel)
        }

        composable(Routes.AnimeCharacterDetailScreen.route) {
            AnimeCharacterDetailScreen(navController,viewModel)
        }

        composable(Routes.PlanetDetailScreen.route) {
            PlanetDetailScreen(navController,viewModel)
        }
    }
}
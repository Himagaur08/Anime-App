package com.android.animeapp.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.android.animeapp.common.NetworkResponse
import com.android.animeapp.domain.models.animecharactermodel.Character
import com.android.animeapp.domain.models.animeplanetmodel.Planets
import com.android.animeapp.presentation.components.AnimeTabRow
import com.android.animeapp.presentation.navigation.Routes
import com.android.animeapp.presentation.viewmodel.AnimeViewModel
import com.android.animeapp.utils.ShimmerList

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: AnimeViewModel = hiltViewModel()
) {
    val characterLists by viewModel.allAnimeCharacter.collectAsState()
    val planetLists by viewModel.allPlanets.collectAsState()
//    var isToggle by remember { mutableStateOf(true) }

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val currentList: NetworkResponse<List<Any>> = if (selectedTabIndex == 0) characterLists else planetLists

    LaunchedEffect(selectedTabIndex) {
        if (selectedTabIndex == 0) {
            viewModel.getAllCharacters()
        } else {
            viewModel.getAllPlanets()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().systemBarsPadding(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Anime App",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold)
                }
            )
        }
    ) { innerPadding ->
        when (val response = currentList) {
            is NetworkResponse.Loading -> {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 8.dp),
                    modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    stickyHeader{
                        AnimeTabRow(
                            modifier = Modifier.fillMaxWidth()
                                .background(color = Color.White),
                            selectedTabIndex = selectedTabIndex,
                            onTabSelected = { selectedTabIndex = it }
                        )
                    }
                    items(10) {
                        ShimmerList()
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            is NetworkResponse.Success<*> -> {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 8.dp),
                    modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    stickyHeader{
                        AnimeTabRow(
                            modifier = Modifier.fillMaxWidth()
                                .background(color = Color.White),
                            selectedTabIndex = selectedTabIndex,
                            onTabSelected = { selectedTabIndex = it }
                        )
                    }
                    when (response.data) {
                        is List<*> -> {
                            if (selectedTabIndex == 0) {
                                val characterList = response.data.filterIsInstance<Character>()
                                items(characterList) { character ->
                                    Spacer(modifier = Modifier.height(8.dp))
                                    CharacterCard(character, navController, viewModel)
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            } else{
                               val planetList = response.data.filterIsInstance<Planets>()
                                items(planetList) { planet ->
                                    Spacer(modifier = Modifier.height(8.dp))
                                    PlanetCard(planet,navController,viewModel)
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }
                }
            }

            is NetworkResponse.Error -> {
                Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {

                        AnimeTabRow(
                            modifier = Modifier.fillMaxWidth()
                                .background(color = Color.White),
                            selectedTabIndex = selectedTabIndex,
                            onTabSelected = { selectedTabIndex = it }
                        )

                    Text(text = response.message)
                }
            }
        }
    }
}



@Composable
fun CharacterCard(
    data : Character,
    navController: NavHostController,
    viewModel: AnimeViewModel = hiltViewModel()
) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(4.dp),
            onClick = {
                viewModel.getACharacter(data)
                navController.navigate(Routes.AnimeCharacterDetailScreen.route)
            }
        ) {
            Row(modifier = Modifier.padding(8.dp)) {
                AsyncImage(
                    model = data.image,
                    contentDescription = "Anime Character Image",
                    modifier = Modifier
                        .size(110.dp),
                    contentScale = ContentScale.Fit)
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = data.name,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(vertical = 8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp, color = Color.Black
                    )
                    Text(
                        text = " Ki : ${data.ki}",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp, color = Color.Gray
                    )
                    Text(
                        text = " Gender : ${data.gender}",
                        modifier = Modifier.padding(vertical = 8.dp),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp, color = Color.Gray
                    )
                }
            }
        }
    }

@Composable
fun PlanetCard(
    data : Planets,
    navController: NavHostController,
    viewModel: AnimeViewModel = hiltViewModel()
) {

        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(4.dp),
            onClick = {
                viewModel.getAPlanet(data)
                navController.navigate(Routes.PlanetDetailScreen.route)
            }) {
            Row(modifier = Modifier.padding(8.dp)) {
                AsyncImage(
                    model = data.image,
                    contentDescription = "Planet Image",
                    modifier = Modifier
                        .size(110.dp)
                        .clip(shape = RoundedCornerShape(14.dp)),
                    contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = data.name,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(vertical = 8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp, color = Color.Black
                    )
                    Text(
                        text = " Is Destroyed: ${data.isDestroyed}",
                        modifier = Modifier.padding(vertical = 8.dp),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp, color = Color.Gray
                    )
                }
            }
        }
    }

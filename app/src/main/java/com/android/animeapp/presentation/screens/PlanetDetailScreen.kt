package com.android.animeapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.android.animeapp.presentation.viewmodel.AnimeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetDetailScreen(
    navController: NavHostController,
    viewModel: AnimeViewModel = hiltViewModel()
){
    val planet = viewModel.selectedPlanet

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(planet?.name ?: "Planets",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back button",
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            item {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = planet?.image,
                            contentDescription = null,
                            modifier = Modifier.size(340.dp)
                                .background(color = Color.White)
                        )

                }
                Row(modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 26.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Name :",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = planet?.name ?: "Unknown",
                        fontSize = 20.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.SemiBold)
                }
                Row(modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 26.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Is Destroyed :",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = planet?.isDestroyed.toString(),
                        fontSize = 20.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.SemiBold)
                }
                Row(modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 26.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Deleted At :",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = planet?.deletedAt.toString(),
                        fontSize = 20.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.SemiBold)
                }
                Column(modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 26.dp)) {
                    Text(text = "Description :",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = planet?.description ?: "Not Available",
                        fontSize = 20.sp,
                        maxLines = 20,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
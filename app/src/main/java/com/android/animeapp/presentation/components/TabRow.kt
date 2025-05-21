package com.android.animeapp.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.animeapp.R


@Composable
fun AnimeTabRow(
    modifier: Modifier,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
){
    val categories = listOf(
        CategoryData("Characters"),
        CategoryData("Planets")
    )

    TabRow(
        selectedTabIndex = selectedTabIndex,
        contentColor = Color.Black,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    .height(3.dp).padding(horizontal = 16.dp), // Customize thickness if needed
                color = colorResource(R.color.teal_700)
            )
        },
        divider = {
            HorizontalDivider(color = Color.LightGray, thickness = 0.6.dp) // <-- customize divider
        }
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = {
                    onTabSelected(index)
                }
            ) {
                Text(text = category.name,
                    fontSize = 26.sp,
                    fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                    color = if (selectedTabIndex == index) Color.Black else Color.DarkGray)
            }
        }
    }
}

data class CategoryData(
    val name: String
)
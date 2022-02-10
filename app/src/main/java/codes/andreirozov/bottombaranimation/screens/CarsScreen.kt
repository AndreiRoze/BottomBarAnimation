package codes.andreirozov.bottombaranimation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.LocalOverScrollConfiguration
import androidx.compose.foundation.gestures.OverScrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarsScreen(navController: NavController) {
    // Use CompositionLocalProvider to create custom OverScrollConfiguration with vertical padding,
    // otherwise glow effect fill be under TopBar and BottomBar
    CompositionLocalProvider(
        LocalOverScrollConfiguration provides OverScrollConfiguration(
            drawPadding = PaddingValues(vertical = 56.dp)
        ),
        content = {
            LazyColumn(
                // Vertical content padding is 64dp, because 56dp is height of TopBar and BottomBar + 8dp visual padding
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 64.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    //Title text
                    item {
                        Text(text = "CARS SCREEN")
                    }

                    // 20 cards with content
                    items(20) {
                        CarCard(
                            navController = navController,
                            name = "Car card number is $it"
                        )
                    }
                }
            )
        }
    )
}

@Composable
fun CarCard(navController: NavController, name: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color.Cyan,
        content = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                content = {
                    Text(
                        text = name
                    )
                    Button(
                        onClick = { navController.navigate("car_details") },
                        content = { Text(text = "Open car details page") }
                    )
                }
            )
        }
    )
}
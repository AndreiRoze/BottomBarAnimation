package codes.andreirozov.bottombaranimation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CarsScreen(navController: NavController) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        content = {
            // Spacer for topBar, otherwise content will be covered by bottomBar
            item {
                Spacer(modifier = Modifier.height(56.dp))
            }

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

            // Spacer for bottomBar, otherwise content will be covered by bottomBar
            item {
                Spacer(modifier = Modifier.height(56.dp))
            }
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
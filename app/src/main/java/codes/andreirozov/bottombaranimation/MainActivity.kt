package codes.andreirozov.bottombaranimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import codes.andreirozov.bottombaranimation.screens.BikesScreen
import codes.andreirozov.bottombaranimation.screens.CarDetailsScreen
import codes.andreirozov.bottombaranimation.screens.CarsScreen
import codes.andreirozov.bottombaranimation.screens.SettingsScreen
import codes.andreirozov.bottombaranimation.ui.theme.BottomBarAnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BottomBarAnimationApp()
        }
    }
}

@Composable
fun BottomBarAnimationApp() {

    // State of bottomBar, set state to false, if current page route is "car_details"
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }

    BottomBarAnimationTheme {
        val navController = rememberNavController()

        // Subscribe to navBackStackEntry, required to get current route
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        // Control TopBar and BottomBar
        when (navBackStackEntry?.destination?.route) {
            "cars" -> {
                // Show BottomBar
                bottomBarState.value = true
            }
            "bikes" -> {
                // Show BottomBar
                bottomBarState.value = true
            }
            "settings" -> {
                // Show BottomBar
                bottomBarState.value = true
            }
            "car_details" -> {
                // Hide BottomBar
                bottomBarState.value = false
            }
        }

        Scaffold(
            bottomBar = {
                BottomBar(
                    navController = navController,
                    bottomBarState = bottomBarState
                )
            },
            content = {
                NavHost(
                    navController = navController,
                    startDestination = NavigationItem.Cars.route,
                ) {
                    composable(NavigationItem.Cars.route) {
                        CarsScreen(
                            navController = navController,
                        )
                    }
                    composable(NavigationItem.Bikes.route) {
                        BikesScreen(
                            navController = navController
                        )
                    }
                    composable(NavigationItem.Settings.route) {
                        SettingsScreen(
                            navController = navController,
                        )
                    }
                    composable(NavigationItem.CarDetails.route) {
                        CarDetailsScreen(
                            navController = navController,
                        )
                    }
                }
            }
        )
    }

}

@Composable
fun BottomBar(navController: NavController, bottomBarState: MutableState<Boolean>) {
    val items = listOf(
        NavigationItem.Cars,
        NavigationItem.Bikes,
        NavigationItem.Settings
    )

    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { item ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.title
                            )
                        },
                        label = { Text(text = item.title) },
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    )
}
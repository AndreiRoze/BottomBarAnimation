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

    // State of topBar, set state to false, if current page route is "car_details"
    val topBarState = rememberSaveable { (mutableStateOf(true)) }

    BottomBarAnimationTheme {
        val navController = rememberNavController()

        // Subscribe to navBackStackEntry, required to get current route
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        // Control TopBar and BottomBar
        when (navBackStackEntry?.destination?.route) {
            "cars" -> {
                // Show BottomBar and TopBar
                bottomBarState.value = true
                topBarState.value = true
            }
            "bikes" -> {
                // Show BottomBar and TopBar
                bottomBarState.value = true
                topBarState.value = true
            }
            "settings" -> {
                // Show BottomBar and TopBar
                bottomBarState.value = true
                topBarState.value = true
            }
            "car_details" -> {
                // Hide BottomBar and TopBar
                bottomBarState.value = false
                topBarState.value = false
            }
        }

        // IMPORTANT, Scaffold from Accompanist, initialized in build.gradle.
        // We use Scaffold from Accompanist, because we need full control of paddings, for example
        // in default Scaffold from Compose we can't disable padding for content from top if we
        // have TopAppBar. In our case it's required because we have animation for TopAppBar,
        // content should be under TopAppBar and we manually control padding for each pages.
        com.google.accompanist.insets.ui.Scaffold(
            bottomBar = {
                BottomBar(
                    navController = navController,
                    bottomBarState = bottomBarState
                )
            },
            topBar = {
                TopBar(
                    navController = navController,
                    topBarState = topBarState
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

@Composable
fun TopBar(navController: NavController, topBarState: MutableState<Boolean>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val title: String = when (navBackStackEntry?.destination?.route ?: "cars") {
        "cars" -> "Cars"
        "bikes" -> "Bikes"
        "settings" -> "Settings"
        "car_details" -> "Cars"
        else -> "Cars"
    }

    AnimatedVisibility(
        visible = topBarState.value,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            TopAppBar(
                title = { Text(text = title) },
            )
        }
    )
}
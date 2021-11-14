package codes.andreirozov.bottombaranimation

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Cars : NavigationItem("cars", R.drawable.car_icon, "Cars")
    object Bikes : NavigationItem("bikes", R.drawable.bike_icon, "Bikes")
    object Settings : NavigationItem("settings", R.drawable.settings_icon, "Settings")
    object CarDetails : NavigationItem("car_details", R.drawable.car_icon, "Car details")
}
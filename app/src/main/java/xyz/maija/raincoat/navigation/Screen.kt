package xyz.maija.raincoat.navigation

/*
    Keeps track of all the screens that the navigation will need to display and their routes.
 */
// sealed means that subclasses are predetermined and finite
sealed class Screen(val route: String) {

    // create an object for each screen of our app and it's route
    object HomePage: Screen(route = "home_page")
    object  SettingsPage: Screen(route = "settings_page")
    object  LocationPage: Screen(route = "location_page")
    object  WelcomeWizard1: Screen(route = "welcome_wizard_1")
    object  WelcomeWizard2: Screen(route = "welcome_wizard_2")

} // sealed class Screen
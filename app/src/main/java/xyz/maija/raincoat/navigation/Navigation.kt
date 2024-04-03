package xyz.maija.raincoat.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import xyz.maija.raincoat.classes.RaincoatViewModel
import xyz.maija.raincoat.classes.User
import xyz.maija.raincoat.views.Homepage
import xyz.maija.raincoat.views.Location
import xyz.maija.raincoat.views.Settings
import xyz.maija.raincoat.views.WelcomeWizard1
import xyz.maija.raincoat.views.WelcomeWizard2


@Composable
fun Navigation() {

    val navController = rememberNavController() // navigation controller state
    val raincoatViewModel: RaincoatViewModel = viewModel()


    NavHost(
        navController = navController,
        startDestination = Screen.WelcomeWizard1.route, // homepage or welcome wizard
    ) {
        // builder
        composable(Screen.HomePage.route) {
            Homepage(navController)
        } // composable screen

        composable(Screen.WelcomeWizard1.route) {
            WelcomeWizard1(
                navController,
                setPreviousScreen = { raincoatViewModel.setPreviousScreen(it) },
                setHairstyle = { raincoatViewModel.setHair(it) },
                setHotCold = { raincoatViewModel.setHotCold(it) }
            )
        } // composable screen

        composable(Screen.WelcomeWizard2.route) {
            WelcomeWizard2(navController)
        } // composable screen

        composable(Screen.SettingsPage.route) {
            Settings(navController)
        } // composable screen

        composable(Screen.LocationPage.route) {
            Location(navController)
        } // composable screen


//        // arguments are passed as part of the route url
//        // use /{...} - value for each required param
//        // ex /{name}/{id}/{birthday}/...
//        // use ?name={name} or ?name={name}&id={id}... <- these are for optional params
//        // app will crash if don't provide required params!
//        // should also do validation
//        composable(
//            Screen.SettingsPage.route + "/{name}",
//            arguments = listOf( // always needs to be a list even if it's only one
//                navArgument("name") {
//                    type = NavType.StringType // also defaults to string
//                    defaultValue = ""
//                    nullable = true
//                }
//            )
//        ) { entry ->
//            // can get an entry of each type for each argument
//            // it's a bundle
//            Text("Argument: ${entry.arguments?.getString("name")}")
//        } // composable main screen

    } // NavHost

} // Navigation


/*

    List of Screens
    - Welcome Wizard 1
        - isLocationNextScreen? // i don't think this is needed (if we have location, do next wizard screen, otherwise location
        - location?
    - Welcome Wizard 2
        - isWelcomeWizard: Bool
        - welcomeUser?
        - location?
    - Homepage
    - Settings
        - hotCold: Double
        - useCelsius: Bool
        - hairstyle: hairstyle?
        - wentToSettingsReload: Bool -> gonna ignore this one for now
    - Location
        - fromSettings: Bool
        - welcomeUser?:

 */
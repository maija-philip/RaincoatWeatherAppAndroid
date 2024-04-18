package xyz.maija.raincoat.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.maija.raincoat.data.api.model.RaincoatViewModel
import xyz.maija.raincoat.ui.views.Homepage
import xyz.maija.raincoat.ui.views.LocationScreen
import xyz.maija.raincoat.ui.views.Settings
import xyz.maija.raincoat.ui.views.WelcomeWizard1
import xyz.maija.raincoat.ui.views.WelcomeWizard2


@Composable
fun Navigation() {

    val navController = rememberNavController() // navigation controller state
    val raincoatViewModel: RaincoatViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.WelcomeWizard1.route, // TODO: homepage or welcome wizard based on data
    ) {
        // builder
        composable(Screen.HomePage.route) {

            // only call the api once
            if (!raincoatViewModel.gotWeather) {
                raincoatViewModel.getWeatherData()
                raincoatViewModel.setGotWeather(true)
            }

            Homepage(
                navController,
                user = raincoatViewModel.user,
                weather = raincoatViewModel.weatherData,
                weatherErrorMessage = raincoatViewModel.weatherErrorMessage,
                weatherLoading = raincoatViewModel.weatherLoading,
                locationErrorMessage = raincoatViewModel.geoLocationErrorMessage,
                setPreviousScreen = { raincoatViewModel.setPreviousScreen(it) },
            )
        } // composable screen

        composable(Screen.WelcomeWizard1.route) {
            WelcomeWizard1(
                navController,
                user = raincoatViewModel.user,
                setPreviousScreen = { raincoatViewModel.setPreviousScreen(it) },
                setHairstyle = { raincoatViewModel.setHair(it) },
                setHotCold = { raincoatViewModel.setHotCold(it) }
            )
        } // composable screen

        composable(Screen.WelcomeWizard2.route) {
            WelcomeWizard2(
                navController,
                previousScreen = raincoatViewModel.previousScreen,
                setSkinColor = { raincoatViewModel.setSkinColor(it) },
                setPreviousScreen = { raincoatViewModel.setPreviousScreen(it) },
            )
        } // composable screen

        composable(Screen.SettingsPage.route) {
            Settings(
                navController,
                user = raincoatViewModel.user,
                setHotCold = { raincoatViewModel.setHotCold(it) },
                setHairstyle = { raincoatViewModel.setHair(it) },
                setPreviousScreen = { raincoatViewModel.setPreviousScreen(it) },
                reGetWeatherMessage = { raincoatViewModel.weatherData?.resetTempMessage(raincoatViewModel.user) }
            )
        } // composable screen

        composable(Screen.LocationPage.route) {
            LocationScreen(
                navController,
                previousScreen = raincoatViewModel.previousScreen,
                setGotWeather = { raincoatViewModel.setGotWeather(it) },
                setPreviousScreen = { raincoatViewModel.setPreviousScreen(it) },
                getLocationData = { postalCode, country -> raincoatViewModel.getLocationData(postalCode, country) }
            )
        } // composable screen

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
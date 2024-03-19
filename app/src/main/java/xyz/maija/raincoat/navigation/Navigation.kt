package xyz.maija.raincoat.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


@Composable
fun Navigation() {

    val navController = rememberNavController() // navigation controller state

    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route,
    ) {
        // builder
        composable(Screen.MainScreen.route) {
            Text("Main Screen")
        } // composable main screen

        // arguments are passed as part of the route url
        // use /{...} - value for each required param
        // ex /{name}/{id}/{birthday}/...
        // use ?name={name} or ?name={name}&id={id}... <- these are for optional params
        // app will crash if don't provide required params!
        // should also do validation
        composable(
            Screen.DetailScreen.route + "/{name}",
            arguments = listOf( // always needs to be a list even if it's only one
                navArgument("name") {
                    type = NavType.StringType // also defaults to string
                    defaultValue = ""
                    nullable = true
                }
            )
        ) { entry ->
            // can get an entry of each type for each argument
            // it's a bundle
            Text("Argument: ${entry.arguments?.getString("name")}")
        } // composable main screen

    } // NavHost

} // Navigation
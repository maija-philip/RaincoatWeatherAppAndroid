package xyz.maija.raincoat.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import xyz.maija.raincoat.data.api.model.RaincoatViewModel

@Composable
fun Router() {

    val raincoatViewModel: RaincoatViewModel = viewModel()
    var isJustLaunched by remember { mutableStateOf(true) }

    var initialScreen: String = Screen.HomePage.route
    val userListFromStorage = raincoatViewModel.userList.collectAsState(initial = emptyList())

    // switch to Welcome Wizard if there is no stored data (means they are a new user)
    // else put the data in the view model
    Log.d("MEP", "Navigation: User List: ${userListFromStorage.value}")
//    if (isJustLaunched) {
//        isJustLaunched = false
//
//    }

    if (userListFromStorage.value.isEmpty()) {
        Log.d("MEP", "Navigation: User List is Empty")
        initialScreen = Screen.WelcomeWizard1.route
    } else {
        Log.d("MEP", "Navigation: User List is not Empty")
        val firstItem = userListFromStorage.value[0]
        raincoatViewModel.setUser(firstItem)
    }

    Navigation(initialScreen, raincoatViewModel)

} // Router
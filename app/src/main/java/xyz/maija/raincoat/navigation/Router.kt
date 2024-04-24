package xyz.maija.raincoat.navigation

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import xyz.maija.raincoat.classes.AskForPermission
import xyz.maija.raincoat.data.api.model.RaincoatViewModel
import java.security.Permissions

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Router() {

    // set up stuff for navigation

    val raincoatViewModel: RaincoatViewModel = viewModel()
    var initialScreen: String = Screen.HomePage.route
    val userListFromStorage = raincoatViewModel.userList.collectAsState(initial = emptyList())

    // switch to Welcome Wizard if there is no stored data (means they are a new user)
    // else put the data in the view model
    if (userListFromStorage.value.isEmpty()) {
        initialScreen = Screen.WelcomeWizard1.route
    } else {
        val firstItem = userListFromStorage.value[0]
        raincoatViewModel.setUser(firstItem)
    }

    // set up permissions stuff
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    ) // permissionState

    val context = LocalContext.current
    AskForPermission(
        permission = Manifest.permission.ACCESS_FINE_LOCATION,
        permissionName = "Location",
        reason = "Raincoat needs your fine location to give you accurate weather for your location. If you deny, you may enter your country and postal code instead.",
        context = context
    )

    Navigation(initialScreen, raincoatViewModel)

} // Router
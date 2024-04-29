package xyz.maija.raincoat.navigation

import android.Manifest
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import xyz.maija.raincoat.classes.AskForPermission
import xyz.maija.raincoat.classes.Location
import xyz.maija.raincoat.classes.LocationLiveData
import xyz.maija.raincoat.data.api.model.RaincoatViewModel
import java.security.Permissions

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Router(
    raincoatViewModel: RaincoatViewModel
) {

    // set up stuff for navigation
    val context = LocalContext.current
    var initialScreen: String = Screen.HomePage.route
    val userListFromStorage = raincoatViewModel.userList.collectAsState(initial = emptyList())
    var ranAlready by remember { mutableStateOf(false) }

    // switch to Welcome Wizard if there is no stored data (means they are a new user)
    // else put the data in the view model
    if (userListFromStorage.value.isEmpty()) {
        initialScreen = Screen.WelcomeWizard1.route
    } else {
        val firstItem = userListFromStorage.value[0]
        raincoatViewModel.setUser(firstItem)
    }

    // ask for locations permission
    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    if (!ranAlready) {
        ranAlready = true
        AskForPermission(
            permission = Manifest.permission.ACCESS_FINE_LOCATION,
            permissionName = "Location",
            permissionState = locationPermissionState,
            reason = "Raincoat needs your fine location to give you accurate weather for your location. If you deny, you may enter your country and postal code instead.",
            context = context
        )
    }


    // get the location and only save data as apart of user if we can get the latitude and longitude
    val currentLocation by raincoatViewModel.getLocationLiveData().observeAsState()
    // Log.d("MEP", "Router: currentLocation $currentLocation")
    if (currentLocation?.longitude != null) {
        // Log.d("MEP", "Router: got location")
        raincoatViewModel.setLocation(Location(
            locationName = "Your Location",
            shortname = "Your Location",
            latitude = currentLocation?.latitude?.toDouble() ?: 0.0,
            longitude = currentLocation?.longitude?.toDouble() ?: 0.0
        ))
    } // if not null

    Navigation(initialScreen, raincoatViewModel, currentLocation)

} // Router
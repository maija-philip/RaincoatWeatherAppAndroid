package xyz.maija.raincoat.navigation

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import xyz.maija.raincoat.classes.AskForPermission
import xyz.maija.raincoat.classes.Location
import xyz.maija.raincoat.classes.LocationLiveData
import xyz.maija.raincoat.data.api.model.RaincoatViewModel
import xyz.maija.raincoat.ui.views.LogoScreen
import xyz.maija.raincoat.utils.rubikFont
import java.security.Permissions

/*
    start’s up the view model, asks for location permission as early as we can, as well as displaying the navigation which has the current screen.
 */

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Router(
    raincoatViewModel: RaincoatViewModel
) {

    // set up stuff for navigation
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
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
    if (currentLocation?.longitude != null) {
        raincoatViewModel.setLocation(Location(
            locationName = "Your Location",
            shortname = "Your Location",
            latitude = currentLocation?.latitude?.toDouble() ?: 0.0,
            longitude = currentLocation?.longitude?.toDouble() ?: 0.0
        ))
    } // if not null
    var shouldLoad by remember { mutableStateOf(locationPermissionState.status.isGranted && currentLocation == null) }


    // loads for 30s then decides its a lost cause in order to give the location time to be fetched
    // if location is fetched faster than 30s it displays and doesn't continue to wait
    if (shouldLoad) {
        LaunchedEffect(key1 = 0, block = {
            scope.launch {
                delay(5000) // 5 seconds
                shouldLoad = false
            }
        })

        LogoScreen()
    } else {
        Navigation(initialScreen, raincoatViewModel, currentLocation)
    } // check to see if need to wait for location

} // Router

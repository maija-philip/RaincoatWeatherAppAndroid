package xyz.maija.raincoat.ui.views

import android.Manifest
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import xyz.maija.raincoat.classes.AskForPermission
import xyz.maija.raincoat.classes.Hairstyle
import xyz.maija.raincoat.classes.Location
import xyz.maija.raincoat.data.entities.User
import xyz.maija.raincoat.navigation.Screen
import xyz.maija.raincoat.utils.rubikFont
import xyz.maija.raincoat.ui.theme.RaincoatTheme
import xyz.maija.raincoat.utils.LocationDetails

/*
    Provides options for users to change the information they entered in the wizard, as well as changing their location as many times as they want
 */

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun Settings(
    navController: NavController,
    user: User,
    setHotCold: (Double) -> Unit,
    setHairstyle: (Hairstyle) -> Unit,
    setUseCelsius: (Boolean) -> Unit,
    setUserLocation: (Location) -> Unit,
    setPreviousScreen: (Screen) -> Unit,
    reGetWeatherMessage: () -> Unit,
    updateCustomerInDB: () -> Unit,
    currentLocation: LocationDetails?,
    modifier: Modifier = Modifier
) {

    // Declare State Variables
    var hotcold by remember { mutableFloatStateOf(user.hotcold.toFloat()) }
    var hair by remember { mutableStateOf(user.hair) }
    var useCelsius by remember { mutableStateOf(user.useCelsius) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var shouldAskPermission by remember { mutableStateOf(false) }

    // ask for locations permission
    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    if (shouldAskPermission) {
        shouldAskPermission = false
        Log.d("MEP", "Settings: ask for permission")
        AskForPermission(
            permission = Manifest.permission.ACCESS_FINE_LOCATION,
            permissionName = "Location",
            permissionState = locationPermissionState,
            reason = "Raincoat needs your fine location to give you accurate weather for your location. If you deny, you may enter your country and postal code instead.",
            context = context
        )
    }

    // UI
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Settings",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = rubikFont,
                fontSize = 40.sp,
                lineHeight = 45.sp,
                textAlign = TextAlign.Start,
                // modifier = Modifier.padding(bottom = 40.dp)
            ) // Welcome

            RunHotOrCold(hotcold = hotcold, updateHotCold = { newHotCold ->
                reGetWeatherMessage()
                setHotCold(newHotCold.toDouble())
                hotcold = newHotCold
                updateCustomerInDB()
            }, inSettings = true)

            SectionHeader(text = "General")
            SectionLink(title = "Location", data = user.location?.locationName ?: "No Location") {
                // onclick - navigate to location screen
                setPreviousScreen(Screen.SettingsPage)
                navController.navigate(Screen.LocationPage.route) {
                    launchSingleTop = true
                } // navcontroller.navigate
            }

            // toggle for useCelsius
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        setUseCelsius(!useCelsius)
                        useCelsius = !useCelsius
                        updateCustomerInDB()
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Use Celsius",
                    fontFamily = rubikFont,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ) // title

                Switch(
                    checked = useCelsius,
                    onCheckedChange = {
                        setUseCelsius(!useCelsius)
                        useCelsius = !useCelsius
                        updateCustomerInDB()
                    }
                ) // Switch
            }

            SectionHeader(text = "Looks")
            // SectionLink(title = "Theme", data = "Blue")
            SectionLink(title = "Hair", data = hair.toString().lowercase()) {
                // onclick
                showBottomSheet = true
            }
            SectionLink(title = "Skin Color", data = "") {
                // onclick - navigate to welcomewizard2
                setPreviousScreen(Screen.SettingsPage)
                navController.navigate(Screen.WelcomeWizard2.route) {
                    launchSingleTop = true
                } // navcontroller.navigate
            } // skin color link}
        }

        // get current location
        // Spacer(modifier = Modifier.fillMaxHeight())
        FilledTonalButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                shouldAskPermission = true

                // report if we can not get the current location
                if (currentLocation?.longitude == null) {
                    Toast.makeText(context, "Can not get location, check your permissions", Toast.LENGTH_LONG).show()
                    return@FilledTonalButton
                }

                setUserLocation(
                    Location(
                        locationName = "Your Location",
                        shortname = "Your Location",
                        latitude = currentLocation.latitude.toDouble(),
                        longitude = currentLocation.longitude.toDouble()
                    )
                ) // set User location
                updateCustomerInDB()
                Toast.makeText(context, "Updated Location", Toast.LENGTH_LONG).show()

            } // onclick
        ) {
            Text(
                text = "Use Current Location",
                fontFamily = rubikFont,
                color = MaterialTheme.colorScheme.primary
            ) // Text
        } // Filled Tonal Button

        // modal bottom sheet for selecting hair
        if (showBottomSheet) {
            ModalBottomSheet(onDismissRequest = {
                showBottomSheet = false
            }) {
                Box(modifier = Modifier.padding(24.dp)) {
                    ChooseAHairstyle(
                        updateVisible2ndSection = {},
                        hairstyle = hair,
                        updateHairstyle = { newHair ->
                            hair = newHair
                            setHairstyle(newHair)
                            updateCustomerInDB()
                        },
                        reGetWeatherMessage = { reGetWeatherMessage() }
                    ) // chose a hairstyle
                }

            } // ModalBottomSheet
        } // show Bottom Sheet

    } // overarching column
} // Settings

@Composable
fun SectionHeader(text: String) {
    Text(
        text = text,
        fontFamily = rubikFont,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 40.dp)
    )
} // Section Header

@Composable
fun SectionLink(title: String, data: String, onclick: () -> Unit) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onclick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){

        Text(
            text = title,
            fontFamily = rubikFont,
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onSurface
        ) // title

        Row {

            if (data.isNotEmpty()) {
                Text(
                    text = data,
                    fontFamily = rubikFont,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(end = 8.dp)
                ) // data
            } // if data exists

            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = "Open",
                tint = MaterialTheme.colorScheme.onSurface,
            ) // Icon

        } // arrow and data row

    } // Overarching Row
} // Section Link


@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    val navController = rememberNavController()
    RaincoatTheme {
        Settings(
            navController = navController,
            user = User(),
            setHotCold = { },
            setHairstyle = { },
            setUseCelsius = { },
            setUserLocation = { },
            setPreviousScreen = { },
            reGetWeatherMessage = { },
            updateCustomerInDB = { },
            currentLocation = null
        )
    }
}
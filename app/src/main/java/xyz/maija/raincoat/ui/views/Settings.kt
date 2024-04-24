package xyz.maija.raincoat.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import xyz.maija.raincoat.classes.Hairstyle
import xyz.maija.raincoat.data.entities.User
import xyz.maija.raincoat.navigation.Screen
import xyz.maija.raincoat.utils.rubikFont
import xyz.maija.raincoat.ui.theme.RaincoatTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(
    navController: NavController,
    user: User,
    setHotCold: (Double) -> Unit,
    setHairstyle: (Hairstyle) -> Unit,
    setUseCelsius: (Boolean) -> Unit,
    setPreviousScreen: (Screen) -> Unit,
    reGetWeatherMessage: () -> Unit,
    modifier: Modifier = Modifier
) {

    // TODO: add use celsius switch

    // Declare State Variables
    var hotcold by remember { mutableFloatStateOf(user.hotcold.toFloat()) }
    var hair by remember { mutableStateOf(user.hair) }
    var useCelsius by remember { mutableStateOf(user.useCelsius) }
    var showBottomSheet by remember { mutableStateOf(false) }

    // UI
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
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
        }, inSettings = true)

        SectionHeader(text = "General")
        SectionLink(title = "Location", data = user.location?.locationName ?: "No Location") {
            // onclick - navigate to location screen
            setPreviousScreen(Screen.SettingsPage)
            navController.navigate(Screen.LocationPage.route) {
                launchSingleTop = true
            } // navcontroller.navigate
        }

        // TODO see if looks right + works
        // toggle for useCelsius
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    setUseCelsius(!useCelsius)
                    useCelsius = !useCelsius
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
        } // skin color link

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
                        },
                        reGetWeatherMessage = { reGetWeatherMessage() }
                    ) // chose a hairstyle
                }

            } // ModalBottomSheet
        } // show Bottom Sheet


        // TODO add get current location button

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
            setPreviousScreen = { },
            reGetWeatherMessage = { }
        )
    }
}
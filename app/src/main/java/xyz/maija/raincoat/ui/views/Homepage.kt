package xyz.maija.raincoat.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import xyz.maija.raincoat.classes.Message
import xyz.maija.raincoat.data.entities.User
import xyz.maija.raincoat.classes.Weather
import xyz.maija.raincoat.classes.getWeatherImg
import xyz.maija.raincoat.navigation.Screen
import xyz.maija.raincoat.utils.rubikFont
import xyz.maija.raincoat.ui.theme.RaincoatTheme

/*
    Displays the weather information the user requested as well as a graphic showing how to dress for the weather and a message telling them how to prepare for the weather
 */

@Composable
fun Homepage(
    navController: NavController,
    user: User,
    weather: Weather?,
    weatherErrorMessage: String,
    weatherLoading: Boolean,
    locationErrorMessage: String,
    setPreviousScreen: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {

    val shouldShowWeatherData = !weatherLoading && weatherErrorMessage == "" && locationErrorMessage == ""

    // want even loading and error to align properly
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {

            GoToSettings(
                navController = navController,
                location = user.location?.shortname ?: "No Location",
                setPreviousScreen = { setPreviousScreen(it) }
            )
            if (shouldShowWeatherData && weather != null) {
                WeatherData(user, weather)
            }
        } // Header Text Column

        // check to see whether to display error or data
        if (weatherLoading) {
            Text(text = "Loading...")
        } else if (weatherErrorMessage != "") {
            Text(
                text = "Error: $weatherErrorMessage",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            ) // error text
        }
        else if (locationErrorMessage != "") {
            Text(
                text = "Error: $locationErrorMessage",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            ) // error text
        } else if (weather == null) {
            Text(
                text = "Something went wrong fetching the data. Try again later.",
                fontWeight = FontWeight.Bold,
            ) // error text
        } else if (user.location == null) {
            Text(
                text = "No Location Info",
                fontWeight = FontWeight.Bold,
            ) // error text
        } else {

            WeatherImage(user, weather.message.image)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                WeatherText(weather.message)

            } // Buttons Column
        } // else not loading + no error + weather not null

    } // overarching column

} // Homepage

@Composable
fun WeatherText(
    message: Message
) {
    Text(
        text = buildAnnotatedString {
            append("${message.beginning} ")

            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(message.middle)
            } // blue text

            append(" ${message.end}")

        }, // build Annotated String
        fontWeight = FontWeight.Normal,
        color = MaterialTheme.colorScheme.onSurface,
        fontFamily = rubikFont,
        fontSize = 25.sp,
        lineHeight = 30.sp,
        textAlign = TextAlign.Center
    ) // Welcome
}


@Composable
fun GoToSettings(
    navController: NavController,
    location: String,
    setPreviousScreen: (Screen) -> Unit
) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            imageVector = Icons.Outlined.Settings,
            contentDescription = "Settings",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(end = 8.dp)
                .clickable {
                    setPreviousScreen(Screen.HomePage)
                    // Navigate to Settings
                    navController.navigate(Screen.SettingsPage.route) {
                        launchSingleTop = true
                    } // navcontroller.navigate
                }
        ) // Icon
        Text(
            text = location,
            fontFamily = rubikFont,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        ) // Text
    } // Row
} // GoToSettings

@Composable
fun WeatherData(
    user: User,
    weather: Weather
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        WeatherTemps(
            current = if(user.useCelsius) weather.current else Weather.celsiusToFahrenheit(weather.current),
            low = if(user.useCelsius) weather.min else Weather.celsiusToFahrenheit(weather.min),
            high = if(user.useCelsius) weather.max else Weather.celsiusToFahrenheit(weather.max)
        )

        Row {
            SpecificWeatherInfoColumn(
                percentage = weather.humidity,
                label = "Humidity"
            )
            SpecificWeatherInfoColumn(
                percentage = weather.rainChance,
                label = if(weather.willSnow(user)) "Chance of\nSnow" else "Chance of\nRain"
            )
        } // Row for weather percentages
    } // Row
} // Weather Data

@Composable
fun SpecificWeatherInfoColumn(percentage: Int, label: String) {
    Column (
        modifier = Modifier.padding(start = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${percentage}%",
            fontFamily = rubikFont,
            fontSize = 25.sp,
            color = MaterialTheme.colorScheme.onSurface
        ) // temp low
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = label,
            fontFamily = rubikFont,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        ) // temp high
    } // Column
} // SpecificWeatherColumn

@Composable
fun WeatherTemps(current: Int, low: Int, high: Int) {
    Column (
        modifier = Modifier.padding(vertical = 0.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "${current}º",
            fontFamily = rubikFont,
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            lineHeight = 10.sp,
            overflow = TextOverflow.Clip,
            maxLines = 1,
            modifier = Modifier.padding(0.dp)
        ) // current temp

        Row (
            modifier = Modifier.padding(vertical = 0.dp)
        ) {
            Text(
                text = "${low}º",
                fontFamily = rubikFont,
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.onSurface
            ) // temp low
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = "${high}º",
                fontFamily = rubikFont,
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.onSurface
            ) // temp high
        }
    } // Temp Column
} // WeatherTemps


@Composable
fun WeatherImage(user: User, imageString: String) {
    val imgId = getWeatherImg(name = imageString)

    Image(
        painter = painterResource(id = imgId),
        contentDescription = "Person wearing shorts and a tank top",
        modifier = Modifier
            .scale(1.4f)
            .background(user.skincolor)
            .border(4.dp, MaterialTheme.colorScheme.surface),
        contentScale = ContentScale.Fit,
    )
}


@Preview(showBackground = true)
@Composable
fun HomepagePreview() {
    val navController = rememberNavController()
    RaincoatTheme {
        Homepage(
            navController,
            User(),
            weather = null,
            weatherErrorMessage = "",
            weatherLoading = false,
            locationErrorMessage = "",
            setPreviousScreen = { },
        )
    }
}
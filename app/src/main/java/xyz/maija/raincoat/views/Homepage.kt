package xyz.maija.raincoat.views

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
import xyz.maija.raincoat.R
import xyz.maija.raincoat.classes.User
import xyz.maija.raincoat.navigation.Screen
import xyz.maija.raincoat.utils.rubikFont
import xyz.maija.raincoat.ui.theme.RaincoatTheme


@Composable
fun Homepage(
    navController: NavController,
    user: User,
    setPreviousScreen: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {

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

            GoToSettings(navController = navController, setPreviousScreen = { setPreviousScreen(it) })
            WeatherData()

        } // Header Text Column

        WeatherImage(user)

        Column(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            WeatherText()

        } // Buttons Column
    } // overarching column

}

@Composable
fun WeatherText() {
    Text(
        text = buildAnnotatedString {
            append("Bring a ")

            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("hoodie")
            } // blue text

            append(" for the morning and evening")

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
fun GoToSettings(navController: NavController, setPreviousScreen: (Screen) -> Unit) {
    Row (
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
            text = "Sunnyvale, CA",
            fontFamily = rubikFont,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        ) // Text
    } // Row
} // GoToSettings

@Composable
fun WeatherData() {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        WeatherTemps(current = 20, low = 12, high = 30)

        Row {
            SpecificWeatherInfoColumn(percentage = 87, label = "Humidity")
            SpecificWeatherInfoColumn(percentage = 0, label = "Chance of\nRain")
        }
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
fun WeatherImage(user: User) {
    Image(
        painter = painterResource(id = R.drawable.scorching_bald_light),
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
        Homepage(navController, User(), setPreviousScreen = { })
    }
}
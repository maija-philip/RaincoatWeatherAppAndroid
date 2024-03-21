package xyz.maija.raincoat.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import xyz.maija.raincoat.utils.rubikFont
import xyz.maija.raincoat.ui.theme.RaincoatTheme


@Composable
fun Settings(navController: NavController) {

    // Declare State Variables
    var hotcold by remember { mutableFloatStateOf(50.0f) }

    // UI
    Column(
        modifier = Modifier
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
            hotcold = newHotCold
        }, inSettings = true)

        SectionHeader(text = "General")
        SectionLink(title = "Location", data = "Sunnyvale, CA")

        SectionHeader(text = "Looks")
        // SectionLink(title = "Theme", data = "Blue")
        SectionLink(title = "Hair", data = "Bald")
        SectionLink(title = "Skin Color", data = "")

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
fun SectionLink(title: String, data: String) {
    Row (
        modifier = Modifier.fillMaxWidth(),
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
        Settings(navController = navController)
    }
}
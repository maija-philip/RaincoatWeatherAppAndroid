package xyz.maija.raincoat.ui.views

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import xyz.maija.raincoat.classes.Hairstyle
import xyz.maija.raincoat.R
import xyz.maija.raincoat.data.entities.User
import xyz.maija.raincoat.navigation.Screen
import xyz.maija.raincoat.utils.rubikFont
import xyz.maija.raincoat.ui.theme.RaincoatTheme


@Composable
fun WelcomeWizard1(
    navController: NavController,
    user: User,
    setPreviousScreen: (Screen) -> Unit,
    setHairstyle: (Hairstyle) -> Unit,
    setHotCold: (Double) -> Unit,
    modifier: Modifier = Modifier
) {

    var hairstyle by remember { mutableStateOf(Hairstyle.BALD) }
    var hotcold by remember { mutableStateOf(50.0f) }
    var visible2ndSection by remember {
        mutableStateOf(false)
    }
    val animatedAlpha by animateFloatAsState(
        targetValue = if (visible2ndSection) 1.0f else 0f,
        label = "alpha"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // Annotated String
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(color = MaterialTheme.colorScheme.onSurface)
                ) {
                    append("Welcome to ")
                } // black text

                withStyle(
                    style = SpanStyle(color = MaterialTheme.colorScheme.primary)
                ) {
                    append("Raincoat")
                } // blue text

            }, // build Annotated String
            fontWeight = FontWeight.Bold,
            fontFamily = rubikFont,
            fontSize = 40.sp,
            lineHeight = 45.sp,
            textAlign = TextAlign.Center
        ) // Welcome

        ChooseAHairstyle(
            updateVisible2ndSection = { newVisible ->
                visible2ndSection = newVisible
            },
            hairstyle = hairstyle,
            updateHairstyle = { newHairstyle ->
                hairstyle = newHairstyle
            },
            reGetWeatherMessage = {}
        )

        Column(
            modifier = Modifier
                .graphicsLayer {
                    alpha = animatedAlpha
                }
        ) {
            RunHotOrCold(hotcold = hotcold, updateHotCold = { newHotCold ->
                hotcold = newHotCold
            }, inSettings = false) // RunHotOrCold


            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    onClick = {
                        setHairstyle(hairstyle)
                        setHotCold(hotcold.toDouble())
                        setPreviousScreen(Screen.WelcomeWizard1)

                        Log.d("MEP", "WelcomeWizard1: user.location? ${user.location}")

                        if (user.location == null) {
                            navController.navigate(Screen.LocationPage.route) {
                                launchSingleTop = true
                            } // navcontroller.navigate
                        } else {
                            navController.navigate(Screen.WelcomeWizard2.route) {
                                launchSingleTop = true
                            } // navcontroller.navigate
                        } // else navigate to welcome wizard 2

                    } // onclick
                ) {
                    Text(
                        text = "Continue",
                        fontFamily = rubikFont,
                    )
                } // Continue Button
            } // continue colum
        } // animate column


    } // overarching column

}

@Composable
fun RunHotOrCold(hotcold: Float, updateHotCold: (Float) -> Unit, inSettings: Boolean) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        if (inSettings) {
            SectionHeader(text = "Temperature")
        } else {
            Text(
                text = "Do you tend to run hot or cold?",
                fontFamily = rubikFont,
                fontSize = 18.sp
            )
        }


        Slider(
            value = hotcold,
            onValueChange = {
                updateHotCold(it)
            },
            valueRange = 0.0f .. 100.0f,
            steps = 0, // continuous
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text ="I feel cold\nusually",
                fontFamily = rubikFont,
                fontSize = 15.sp
            )
            Text(
                text = "I feel hot\nusually",
                textAlign = TextAlign.End,
                fontFamily = rubikFont,
                fontSize = 15.sp
                )
        }

    } // Column
} // RunHotOrCold

@Composable
fun ChooseAHairstyle(
    updateVisible2ndSection: (Boolean) -> Unit,
    hairstyle: Hairstyle,
    updateHairstyle: (Hairstyle) -> Unit,
    reGetWeatherMessage: () -> Unit,
) {

    Column (
        modifier = Modifier.padding(vertical = 40.dp)
    ){
        Text(
            text = "Choose a hairstyle",
            fontFamily = rubikFont,
            fontSize = 18.sp
            )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){

            // create the boxes
            HairChoiceBox(updateVisible2ndSection, R.drawable.hair_bald, Hairstyle.BALD, hairstyle, updateHairstyle, reGetWeatherMessage = {
                reGetWeatherMessage()
            })
            HairChoiceBox(updateVisible2ndSection, R.drawable.hair_short, Hairstyle.SHORT, hairstyle, updateHairstyle, reGetWeatherMessage = {
                reGetWeatherMessage()
            })
            HairChoiceBox(updateVisible2ndSection, R.drawable.hair_long, Hairstyle.LONG, hairstyle, updateHairstyle, reGetWeatherMessage = {
                reGetWeatherMessage()
            })

        } // Row
    } // Column

} // ChooseAHairStyle

@Composable
fun HairChoiceBox(
    updateVisible2ndSection: (Boolean) -> Unit,
    image: Int,
    hair: Hairstyle,
    chosenStyle: Hairstyle,
    chooseHair: (Hairstyle) -> Unit,
    reGetWeatherMessage: () -> Unit,
) {

    var hasChosen by remember { mutableStateOf(false) }
    var displayBorder = chosenStyle == hair
    if (!hasChosen) {
        displayBorder = false
    }

    Box(
        modifier = Modifier
            .size(90.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable {
                hasChosen = true
                updateVisible2ndSection(true)
                chooseHair(hair)
                reGetWeatherMessage()
            }
            .border(
                width = if (displayBorder) 6.dp else (-1).dp,
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.primary
            )
    ) {
        Image(painter = painterResource(id = image), contentDescription = "$hair")
    }
} // HairChoiceBox


@Preview(showBackground = true)
@Composable
fun WelcomeWizard1Preview() {
    val navController = rememberNavController()
    RaincoatTheme {
        WelcomeWizard1(
            navController = navController,
            user = User(),
            setPreviousScreen = { },
            setHairstyle = { },
            setHotCold = { },
        )
    }
}
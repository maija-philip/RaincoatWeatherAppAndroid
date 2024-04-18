package xyz.maija.raincoat.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import xyz.maija.raincoat.classes.Location
import xyz.maija.raincoat.data.Country
import xyz.maija.raincoat.navigation.Screen
import xyz.maija.raincoat.utils.rubikFont
import xyz.maija.raincoat.ui.theme.RaincoatTheme

@Composable
fun LocationScreen(
    navController: NavController,
    previousScreen: Screen,
    setGotWeather: (Boolean) -> Unit,
    setPreviousScreen: (Screen) -> Unit,
    getLocationData: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {

    var chosenCountry by remember { mutableStateOf(Country("--", "--")) }
    var enteredLocationState by remember { mutableStateOf("") }
    var isError by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Enter a\nLocation",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = rubikFont,
            fontSize = 40.sp,
            lineHeight = 45.sp,
            textAlign = TextAlign.Center,
            // modifier = Modifier.padding(bottom = 40.dp)
        ) // Enter a Location
        Text(
            text = "Choose you country and enter your postal code",
            fontFamily = rubikFont,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        if (isError) {
            Text(
                text = "Please fill out all fields",
                fontFamily = rubikFont,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.error
            )
        }


        Spacer(modifier = Modifier.height(10.dp))

        CountryDropDown(chosenCountry) {
            chosenCountry = it
        }
        
        RoundedTextBox(
            hint = "94087",
            entered = enteredLocationState,
            action = { newLocation ->
                enteredLocationState = newLocation
            })

        SaveButton(
            navController = navController,
            previousScreen = previousScreen,
            setPreviousScreen = { setPreviousScreen(it) },
            setIsError = { isError = it },
            chosenCountry = chosenCountry,
            enteredPostalCode = enteredLocationState,
            getLocationData = { postalCode, country -> getLocationData(postalCode, country)},
            setGotWeather = { setGotWeather(it) }
        ) // save button

    } // overarching column
} // Settings

@Composable
fun RoundedTextBox(hint: String, entered: String, action: (String) -> Unit) {

    Column(
        modifier = Modifier.padding(horizontal = 32.dp)
    ) {

        TextField(
            value = entered,
            placeholder = {
                Text(text = hint)
            },
            onValueChange = {
                action(it) // perform given action on new string
            }, // onValueChange
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                cursorColor = MaterialTheme.colorScheme.primary,
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(horizontal = 8.dp),
            textStyle = TextStyle(
                fontFamily = rubikFont,
                fontSize = 15.sp
            ),
            visualTransformation = VisualTransformation.None,
        ) // OutlinedTextField
    }

} // RoundedTextBox

@Composable
fun SaveButton(
    navController: NavController,
    previousScreen: Screen,
    setPreviousScreen: (Screen) -> Unit,
    setIsError: (Boolean) -> Unit,
    chosenCountry: Country,
    enteredPostalCode: String,
    getLocationData: (String, String) -> Unit,
    setGotWeather: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            onClick = {

                // if all the feilds aren't there return
                if (chosenCountry.name == "--" || chosenCountry.code == "--" || enteredPostalCode.trim() == "") {
                    setIsError(true)
                    return@Button
                }

                getLocationData(enteredPostalCode, chosenCountry.code)
                setPreviousScreen(Screen.LocationPage)

                // check to see if we go on to welcome wizard 2 or back to settings
                if (previousScreen == Screen.WelcomeWizard1 ) {
                    navController.navigate(Screen.WelcomeWizard2.route) {
                        launchSingleTop = true
                    } // navcontroller.navigate
                } else {
                    // re-get the weather data
                    setGotWeather(false)
                    // go back to settings
                    navController.popBackStack()
                }
            } // onclick - navigate
        ) {
            Text(text = "Save")
        } // Button
    } // Column
} // SaveButton


@Preview(showBackground = true)
@Composable
fun LocationPreview() {
    val navController = rememberNavController()
    RaincoatTheme {
        LocationScreen(
            navController,
            previousScreen = Screen.HomePage,
            setGotWeather = { },
            setPreviousScreen = { },
            getLocationData = { country, postalCode -> }
        )
    }
}
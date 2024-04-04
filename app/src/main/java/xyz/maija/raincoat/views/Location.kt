package xyz.maija.raincoat.views

import android.widget.Spinner
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextRange
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
import xyz.maija.raincoat.navigation.Screen
import xyz.maija.raincoat.utils.rubikFont
import xyz.maija.raincoat.ui.theme.RaincoatTheme


@Composable
fun LocationScreen(
    navController: NavController,
    previousScreen: Screen,
    setPreviousScreen: (Screen) -> Unit,
    setLocation: (Location) -> Unit,
    modifier: Modifier = Modifier
) {

    var chosenCountry by remember { mutableStateOf("") }
    var enteredLocationState by remember { mutableStateOf("") }

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
            location = Location(),
            previousScreen = previousScreen,
            setPreviousScreen = { setPreviousScreen(it) },
            setLocation = { setLocation(it) }
        ) // save button

    } // overarching column
} // Settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// used this resource https://alexzh.com/jetpack-compose-dropdownmenu/
// and this https://stackoverflow.com/questions/77336149/dropdown-menu-in-compose-overlaps-system-keyboard
fun CountryDropDown(chosenCountry: String, updateChosenCountry: (String) -> Unit) {

    var expanded by remember { mutableStateOf(false) }
    val countryList = arrayOf("Korea", "Italy", "Latvia", "Kenya", "India", "Nepal")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
            .padding(32.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 8.dp),
    ) {
        TextField(
            value = chosenCountry,
            onValueChange = {
                updateChosenCountry(it)
            },
            label = { Text(text = "Choose a country") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            readOnly = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                cursorColor = MaterialTheme.colorScheme.primary,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
                .fillMaxWidth()
                .clip(RoundedCornerShape(50.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(horizontal = 8.dp),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.exposedDropdownSize(),
        ) {
            countryList.forEach { country ->
                DropdownMenuItem(
                    text = { Text(text = country) },
                    onClick = {
                        updateChosenCountry(country)
                        expanded = false
                    } // onclick
                ) // dropdown menu item
            } // for each country
        } // exposed dropdown menu
    } // Exposed dropdown menu box

} // CountryDropDown

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
                keyboardType = KeyboardType.Text,
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
    location: Location,
    previousScreen: Screen,
    setPreviousScreen: (Screen) -> Unit,
    setLocation: (Location) -> Unit,
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
                // val tempPrevScreen = previousScreen don't think i need this bc i'm not getting a changed version of previous screen
                setLocation(location)
                setPreviousScreen(Screen.LocationPage)

                // check to see if we go on to welcome wizard 2 or back to settings
                if (previousScreen == Screen.WelcomeWizard1 ) {
                    navController.navigate(Screen.WelcomeWizard2.route) {
                        launchSingleTop = true
                    } // navcontroller.navigate
                } else {
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
            setPreviousScreen = { },
            setLocation = { }
        )
    }
}
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
import xyz.maija.raincoat.utils.rubikFont
import xyz.maija.raincoat.ui.theme.RaincoatTheme


@Composable
fun Location(modifier: Modifier = Modifier) {

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

        ExpirationTimeMenuBox()
        
        RoundedTextBox(
            hint = "94087",
            entered = enteredLocationState,
            action = { newLocation ->
                enteredLocationState = newLocation
            })

        SaveButton(enteredLocationState)

    } // overarching column
} // Settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// used this resource https://alexzh.com/jetpack-compose-dropdownmenu/
fun CountryDropDown(chosenCountry: String, updateChosenCountry: (String) -> Unit) {

    var expanded by remember { mutableStateOf(false) }
    val countryList = arrayOf("Korea", "Italy", "Latvia", "Kenya", "India", "Nepal")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 8.dp),
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
        ) {
            TextField(
                value = chosenCountry,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = false }
            ) {

                countryList.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            updateChosenCountry(item)
                            expanded = false
                        } // onclick for item
                    ) // dropdown menu item
                } // for each country
            } // dropdown menu
        } // Exposed dropdown box
    } // Box

} // CountryDropDown


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpirationTimeMenuBox() {
    val suggestions = listOf("Dzień", "Tydzień", "Miesiąc", "Rok", "Dzień")
    var isExpanded by remember { mutableStateOf(false) }
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(""))
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp)
    ) {
        TextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            label = { Text(text = "Termin") },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier.exposedDropdownSize(),
        ) {
            suggestions.forEach { suggestion ->
                DropdownMenuItem(
                    text = { Text(text = suggestion) },
                    onClick = {
                        textFieldValue = TextFieldValue(suggestion, selection = TextRange(suggestion.length))
                        isExpanded = false
                    }
                )
            }
        }
    }
}


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
fun SaveButton(enteredLocation: String) {
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
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Save")
        } // Button
    } // Column
} // SaveButton


@Preview(showBackground = true)
@Composable
fun LocationPreview() {
    RaincoatTheme {
        Location()
    }
}
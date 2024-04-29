package xyz.maija.raincoat.ui.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import xyz.maija.raincoat.data.Countries
import xyz.maija.raincoat.data.Country

/*
    Used in the Location View in order to pick from a searchable list of all the countries supported by the API
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
// used this resource https://alexzh.com/jetpack-compose-dropdownmenu/
// and this https://stackoverflow.com/questions/77336149/dropdown-menu-in-compose-overlaps-system-keyboard
fun CountryDropDown(chosenCountry: Country, updateChosenCountry: (Country) -> Unit) {

    var expanded by remember { mutableStateOf(false) }
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var filteredSuggestions by remember {
        mutableStateOf(Countries.list)
    }

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
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
            },
            label = { Text(text = "Choose a country") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            // readOnly = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
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
        if (!textFieldValue.text.isEmpty()) {
            filteredSuggestions = Countries.list.filter { country -> country.name.contains(textFieldValue.text, ignoreCase = true) }
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.exposedDropdownSize(),
        ) {
            filteredSuggestions.forEach { country ->
                DropdownMenuItem(
                    text = { Text(text = country.name) },
                    onClick = {
                        textFieldValue = TextFieldValue(country.name, selection = TextRange(country.name.length))
                        updateChosenCountry(country)
                        expanded = false
                    } // onclick
                ) // dropdown menu item
            } // for each country
        } // exposed dropdown menu
    } // Exposed dropdown menu box

} // CountryDropDown

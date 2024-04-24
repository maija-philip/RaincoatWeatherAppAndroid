package xyz.maija.raincoat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import xyz.maija.raincoat.data.api.model.RaincoatViewModel
import xyz.maija.raincoat.navigation.Navigation
import xyz.maija.raincoat.navigation.Router
import xyz.maija.raincoat.ui.theme.RaincoatTheme


/*
    Set up the app and display the Navigation which shows which screen we are currently on
 */
class MainActivity : ComponentActivity() {

    private lateinit var raincoatViewModel: RaincoatViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            raincoatViewModel = ViewModelProvider(this).get(RaincoatViewModel::class.java)

            RaincoatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {

                    Router(raincoatViewModel)

                } // Surface
            } // Raincoat Theme
        } // setContent
    } // onCreate
} // MainActivity

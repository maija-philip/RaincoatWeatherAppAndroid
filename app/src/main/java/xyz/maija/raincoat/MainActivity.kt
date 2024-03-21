package xyz.maija.raincoat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import xyz.maija.raincoat.navigation.Navigation
import xyz.maija.raincoat.ui.theme.RaincoatTheme
import xyz.maija.raincoat.views.Homepage
import xyz.maija.raincoat.views.Location
import xyz.maija.raincoat.views.Settings
import xyz.maija.raincoat.views.WelcomeWizard1
import xyz.maija.raincoat.views.WelcomeWizard2

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RaincoatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {

                    Navigation()
                    // Location()

                } // Surface
            } // Raincoat Theme
        } // setContent
    } // onCreate
} // MainActivity
